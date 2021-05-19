package com.example.newwriters.ui.admin.home.ui.uploadbooks

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newwriters.R
import com.example.newwriters.repository.BookRepository
import com.example.newwriters.repository.NotificationRepository
import com.example.newwriters.ui.model.Book
import com.example.newwriters.ui.model.Notification
import com.example.newwriters.ui.utils.notif
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.FileUtils.copyInputStreamToFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws


class UploadBookFragment : Fragment() {

    private lateinit var uploadBookViewModel: UploadBookViewModel
    private lateinit var CoverPage: ImageView
    private lateinit var up_Book: Button
    private lateinit var uploadBook: Button
    private lateinit var bk_name: TextView
    private lateinit var offer: EditText
    private lateinit var price: EditText
    private lateinit var AuthorName: EditText
    private lateinit var bookName: EditText
    private lateinit var decription: EditText
    private val REQUEST_FILE_CODE = 2;
    private var fileUrl: String? = null

    private lateinit var mFile: File
    lateinit var selectedFile: Uri
    lateinit var displayName:String;
    private val REQUEST_GALLERY_CODE=0;
    private var bookid:String?=null;
    private val REQUEST_CAMERA_CODE=1;
    private var imageUrl:String?=null;
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        uploadBookViewModel =
            ViewModelProvider(this).get(UploadBookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upload_book, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        uploadBookViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoverPage=view.findViewById(R.id.CoverPage)
        bk_name=view.findViewById(R.id.bk_name)
        bookName=view.findViewById(R.id.bookName)
        AuthorName=view.findViewById(R.id.AuthorName)
        decription=view.findViewById(R.id.decription)
        up_Book=view.findViewById(R.id.up_Book)
        uploadBook=view.findViewById(R.id.uploadBook)
        offer=view.findViewById(R.id.offer)
        price=view.findViewById(R.id.price)
        CoverPage.setOnClickListener(){
            popup()
        }
        up_Book.setOnClickListener(){
            openFile()
        }
        uploadBook.setOnClickListener(){
            uploadBook()
        }
    }
    private fun popup(){
        val popupMenu= PopupMenu(requireContext(), CoverPage)
        popupMenu.menuInflater.inflate(R.menu.pop_up_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gallery -> {
                    openGallery()
                    true
                }
                R.id.camera -> {
                    openCamera()
                    true
                }
                else -> false
            }

        })
        popupMenu.show()
    }
    private fun openGallery(){
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }
    private fun openCamera(){
        val cameraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }
    private fun openFile() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/*"
        intent = Intent.createChooser(intent, "Choose a file")
        startActivityForResult(intent, REQUEST_FILE_CODE)

    }
    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = requireActivity().contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                CoverPage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
                Toast.makeText(requireContext(), "this is imgeurl: $imageUrl", Toast.LENGTH_LONG).show()
                } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                    val imageBitmap = data.extras?.get("data") as Bitmap
                    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                    val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                    imageUrl = file!!.absolutePath
                    CoverPage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                }
            else if (requestCode == REQUEST_FILE_CODE && data != null) {
                selectedFile = data.data!!
                data.data?.also {
                    fileUrl = it.path
                    mFile = importFile(it);

                }
            }
        }
    }

    private fun importFile(uri: Uri): File {
        val fileName: String = getFileName(uri)
        Log.i("TAGGER", "Display Name: $fileName")
        // The temp file could be whatever you want
        val directory =requireActivity().application.cacheDir
        val Tempfile  = File(directory,fileName);
        val fileCopy: File = copyToTempFile(uri, Tempfile)
        return fileCopy;
    }

    // return the fileName from given URI
    private fun getFileName(uri: Uri): String {
        // The query, because it only applies to a single document, returns only
        // one row. There's no need to filter, sort, or select fields,
        // because we want all fields for one document.
        val cursor: Cursor? = requireActivity().contentResolver.query(
                uri, null, null, null, null, null
        )
        cursor?.use {
            // moveToFirst() returns false if the cursor has 0 rows. Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (it.moveToFirst()) {

                // Note it's called "Display Name". This is
                // provider-specific, and might not necessarily be the file name.
                val fileName: String =
                        it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                displayName = fileName
                bk_name.setText("$displayName")
                bk_name.setTextColor(Color.parseColor("#006400"))
            }
        }
        return displayName;
    }
    //copies the inputStream from given file to a temporary file.
    @Throws(IOException::class)
    private fun copyToTempFile(uri: Uri, tempFile: File): File {
        // Obtain an input stream from the uri
        val inputStream =requireActivity().contentResolver.openInputStream(uri)
                ?: throw IOException("Unable to obtain input stream from URI")
        // Copy the stream to the temp file
        copyInputStreamToFile(inputStream, tempFile);
        return tempFile
    }
    private fun uploadBook(){
        val book=Book(author_name = AuthorName.text.toString(),book_name = bookName.text.toString(),description = decription.text.toString(),price = price.text.toString(),offer = offer.text.toString())
    try {
        CoroutineScope(Dispatchers.IO).launch {
           val repository=BookRepository()
           val response=repository.uploadBook(book)
           if(response.success==true){
               bookid=response.id!!
               uploadImage(bookid!!)
               uploadBookfile(bookid!!)
               addNotification(id=bookid!!,author_name =  AuthorName.text.toString(),Book_name =bookName.text.toString() )
               withContext(Main){
                   Toast.makeText(requireContext(), "${response.msg}", Toast.LENGTH_SHORT).show()
               }
           }
            else{
               withContext(Main){
                   Toast.makeText(requireContext(), "${response.msg}", Toast.LENGTH_SHORT).show()
               }
           }
        }
    }
    catch (e:Exception){
        Toast.makeText(requireContext(), "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    }
    private fun uploadImage(BookID: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("image/${file.extension}"), file)
            val body =
                MultipartBody.Part.createFormData("cover_page", file.name, reqFile)

            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val studentRepository = BookRepository()
                    val response = studentRepository.uploadCoverPage(BookID, body)
                    if (response.success == true) {
                        withContext(Main) {
                            Toast.makeText(requireContext() ,"Uploaded", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (ex: Exception) {
                    withContext(Main) {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        Log.d("Error uploading image ", ex.toString())
                        Toast.makeText(requireContext(), ex.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun uploadBookfile(bookID: String) {
        val reqFile =
            RequestBody.create(MediaType.parse("file/${mFile.extension}"), mFile)
        val body =
            MultipartBody.Part.createFormData("book", mFile.name, reqFile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = BookRepository()
                val response = studentRepository.UploadBookFile(bookID, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "${response.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    Log.d("Error uploading file ", ex.toString())
                    Toast.makeText(
                        requireContext(),
                        ex.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun addNotification(id:String,author_name:String,Book_name:String){
        val notification=Notification(bookId = id)
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repository=NotificationRepository()
                val response=repository.addNotification(notification)
                if(response.success==true){
                    showHighPriorityNotification(author_name,Book_name)
                }
                else{
                    withContext(Main){
                        Toast.makeText(requireContext(), "error here", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }catch (e:Exception){
            Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
    //------------notification----------------//
    private fun showHighPriorityNotification(author:String,bookname:String){
        val notificationManager= NotificationManagerCompat.from(requireContext())
        val notificationChannels= notif(requireContext())
        notificationChannels.createNotificationChannels()

        val notification= NotificationCompat.Builder(requireContext(),notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.ic_baseline_sms_24)
            .setContentTitle(author)
            .setContentText("Upload $bookname book.")
            .setColor(Color.BLUE)
            .build()
        notificationManager.notify(1,notification)
    }
}