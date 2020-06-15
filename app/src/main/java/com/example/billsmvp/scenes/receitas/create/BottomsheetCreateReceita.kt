package com.example.billsmvp.scenes.receitas.create

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.billsmvp.R
import com.example.billsmvp.models.Receita
import com.example.billsmvp.base.ValidateFields
import com.example.billsmvp.util.getString
import com.example.billsmvp.util.set
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sembozdemir.permissionskt.askPermissions
import kotlinx.android.synthetic.main.bottomsheet_create_receita_content.*
import kotlinx.android.synthetic.main.bottomsheet_create_receita_content.TextInputLayoutDescricao
import kotlinx.android.synthetic.main.bottomsheet_create_receita_content.TextInputLayoutValor
import java.util.*

class BottomsheetCreateReceita(var receita : Receita?) : BottomSheetDialogFragment(), CreateReceitaContract.View {
    val presenter = CreateReceitaPresenter()
    var selectedDate = Date()
    lateinit var dpd : DatePickerDialog
    lateinit var fragmentContext : Context

    val IMAGE : Int = 0
    val CAMERA : Int = 1
    var uri : Uri? = null
    lateinit var mStorage : StorageReference
    var intentImg = Intent()
    var intentImgCamera = Intent()
    var image_uri : Uri? = null
    var filePath : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.bottomsheet_create_receita, container, false)

    }

    override fun onStart() {
        super.onStart()
        presenter.view = this
        fillDate()
        fillData()
        setupButtonAdd()
        setupDatePicker()
        setupBtnImagem()
        setupBtmImagemCamera()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    fun setupBtnImagem() {
        mStorage = FirebaseStorage.getInstance().getReference("Uploads")
        btnImgReceita.setOnClickListener(View.OnClickListener {
                view: View? ->
            intentImg.setType ("image/*")
            intentImg.setAction(Intent.ACTION_GET_CONTENT)
            Intent.createChooser(intentImg, "Selecione imagem")
            startActivityForResult(Intent.createChooser(intentImg, "Selecione imagem"), IMAGE)
        })
    }

    private fun requestCameraPermission() {
        (context as? Activity)?.let {
            it.askPermissions(Manifest.permission.CAMERA) {
                onGranted {
                    openCamera()
                }
            }
        }
    }

    fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Nova imagem")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Da camera")
        context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values).let {
            image_uri = it as Uri
        }
        intentImgCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentImgCamera.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(intentImgCamera, CAMERA)
    }


    fun setupBtmImagemCamera() {
        btnCameraImgReceita.setOnClickListener(View.OnClickListener {
                view: View? ->
            requestCameraPermission()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE) {
                uri = data?.data as Uri
                btnImgReceita.text = "(1)"
                btnCameraImgReceita.visibility = View.INVISIBLE
            }else {
                uri = image_uri
                btnCameraImgReceita.text = "(1)"
                btnImgReceita.visibility = View.INVISIBLE
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun upload(uriAnexo: Uri,userId : String, id: String) {
        mStorage = FirebaseStorage.getInstance().getReference("Uploads/"+userId+"/Receitas/"+id)
        var mReference = mStorage.child(uri?.lastPathSegment.toString())
        try {
            mReference.putFile(uriAnexo).addOnSuccessListener {
                filePath = mReference.path
                FirebaseFirestore.getInstance().collection("receitas").document(id)
                    .update("filePath", filePath)
            }
        }catch (e: Exception) {
            Toast.makeText(fragmentContext, e.toString(), Toast.LENGTH_LONG).show()
        }

    }


    fun validateFields() : Boolean {
        var validateEditTextDescricao = ValidateFields(
            EditTextDescricaoReceita,
            TextInputLayoutDescricao
        )
        var validateEditTextValor = ValidateFields(
            EditTextValorReceita,
            TextInputLayoutValor
        )

        return validateEditTextDescricao.validateDescricao() && validateEditTextValor.validateValor()
    }

    fun getFields() {
        val valor = EditTextValorReceita.valueString.toFloat()
        val descricao = EditTextDescricaoReceita.text.toString()
        val data = Timestamp(selectedDate)
        val recebido = SwitchRecebido.isChecked

        if (receita==null){
            presenter.create(valor, descricao, data, recebido, uri, filePath)
        }else {
            var id = receita?.id.toString()
            var userId = receita?.userId.toString()
            presenter.update(id,userId,valor, descricao, data, recebido,uri, filePath)
        }
    }

    override fun setupButtonAdd() {
        btnAddReceita.setOnClickListener {
            if (!validateFields()) {

            }else{
                getFields()
                this.dismiss()
            }


        }
    }

    override fun fillData() {
        receita?.let {
            EditTextDescricaoReceita.setText(it.descricao)
            EditTextValorReceita.setText(it.valor.toString())
            EditTextDataReceita.setText(it.data.toDate().getString("dd/MM/yyyy"))
            SwitchRecebido.isChecked = it.recebido
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(fragmentContext,message, Toast.LENGTH_SHORT).show()
    }

    private fun fillDate() {
        EditTextDataReceita.setText(selectedDate.getString("dd/MM/yyyy"))
    }


    private fun setupDatePicker() {
        EditTextDataReceita.setOnClickListener {

            val c = Calendar.getInstance()
            c.time = selectedDate
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            dpd = DatePickerDialog(fragmentContext, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

                selectedDate = selectedDate.set(Calendar.YEAR,year)
                selectedDate = selectedDate.set(Calendar.MONTH,monthOfYear)
                selectedDate = selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                fillDate()

            }, year, month, day)
            dpd.show()

        }
    }
}