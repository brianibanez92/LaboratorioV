package ibanez.brian.esoquieroapp.Core;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by brian.ibanez on 05/11/2016.
 */
public class Dialog extends android.support.v4.app.DialogFragment {

    private String title;
    private String message;
    private String positiveButton;
    private String negativeButton;
    private DialogInterface.OnClickListener ml;

    public Dialog(String title, String message, String positiveButton, String negativeButton, DialogInterface.OnClickListener ml)
    {
        this.title = title;
        this.message = message;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.ml = ml;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle bundle)
    {

        if (this.ml == null)
        {
            DialogListener ml = new DialogListener();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (this.title != null)
        {
            builder.setTitle(this.title);
        }

        if(this.message != null)
        {
            builder.setMessage(this.message);
        }

        if(this.positiveButton != null)
        {
            builder.setPositiveButton(this.positiveButton, ml);
        }

        if(this.negativeButton != null)
        {
            builder.setNegativeButton(this.negativeButton, ml);
        }

        return builder.create();
    }

}
