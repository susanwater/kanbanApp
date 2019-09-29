package com.fred.andon.web2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SetMainUrlDialog extends DialogFragment {

	public interface TextEnteredListener {
		public void onTextEntered(String password);
	}

	TextEnteredListener mListener = null;

	public void setOnTextEnteredListener(
			TextEnteredListener listener) {
		this.mListener = listener;
	}

	private void invokeOnTextEntered(String text) {
		if (this.mListener != null) {
			this.mListener.onTextEntered(text);
		}
	}
	
	private EditText _address = null;
	private AlertDialog _dialog = null;
	
	public void setPositiveButtonEnable(boolean isEnable) {
		_dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(isEnable);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ContextThemeWrapper cw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogTheme);
		AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.set_main_url, null);

		builder.setView(view)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						String password = SetMainUrlDialog.this._address.getText().toString();
						SetMainUrlDialog.this.invokeOnTextEntered(password);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SetMainUrlDialog.this.getDialog().cancel();
					}
				});
		_dialog = builder.create();
		_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			public void onShow(DialogInterface dialog) {
				SetMainUrlDialog.this.setPositiveButtonEnable(true);
			} 
		}); 
		
		this._address = (EditText) view.findViewById(R.id.address);
		this._address.setText(AndonConfig.getMainUrl());
		// ?????????????????
		this._address.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
				String str = SetMainUrlDialog.this._address.getText().toString();
				SetMainUrlDialog.this.setPositiveButtonEnable(!str.equals(""));
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Do nothing.
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Do nothing.
			}
		});
		
		return _dialog;
	}
}
