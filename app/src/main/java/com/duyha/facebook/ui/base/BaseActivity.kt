package com.duyha.facebook.ui.base

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.duyha.facebook.R
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        setUp()
        setObservers()
        callViewModel()
    }

    protected abstract fun setUp()

    protected abstract fun callViewModel()

    protected abstract fun setObservers()

    protected abstract fun createViewModel(): VM

    private fun initViewModel() {
        viewModel = createViewModel()
        viewModel.err.observe(this, {
            it.getContentIfNotHandled()?.let { errorEvent -> showError(errorEvent)}
        })
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
    }

    open fun hideLoading() {
    }

    open fun showLoading() {
    }

    open fun showError(err: ErrorEvent) {
        showMessage(err.stringResId())
    }

    private fun showMessage(msgId: Int, okAction: () -> Unit = {}) {
        showMessage(getString(msgId), okAction)
    }

    private fun showMessage(msg: String, okAction: () -> Unit) {
        val content = findViewById<View>(android.R.id.content)
        Snackbar.make(content, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.btn_ok)) { okAction.invoke() }
                .show()
    }

    fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { view ->
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}