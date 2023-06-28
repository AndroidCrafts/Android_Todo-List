package com.hasib.todo.Util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Sugar {
    public static AlertDialog.Builder alertDialog(String title, String message, Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        return alert;
    }
}
