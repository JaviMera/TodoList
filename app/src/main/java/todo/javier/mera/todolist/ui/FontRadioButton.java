package todo.javier.mera.todolist.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by javie on 2/6/2017.
 */

public class FontRadioButton extends RadioButton {
    private Typeface mCustomFont;

    public FontRadioButton(Context context) {
        super(context);

        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Slabo13px-Regular.ttf");
        setTypeface(mCustomFont);
    }

    public FontRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Slabo13px-Regular.ttf");
        setTypeface(mCustomFont);
    }

    public FontRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Slabo13px-Regular.ttf");
        setTypeface(mCustomFont);
    }
}
