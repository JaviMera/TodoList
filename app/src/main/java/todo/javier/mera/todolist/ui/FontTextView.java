package todo.javier.mera.todolist.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * Created by javie on 2/6/2017.
 */

public class FontTextView extends TextView {

    private Typeface mCustomFont;

    public FontTextView(Context context) {
        super(context);

        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Slabo13px-Regular.ttf");
        setTypeface(mCustomFont);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Slabo13px-Regular.ttf");
        setTypeface(mCustomFont);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Slabo13px-Regular.ttf");
        setTypeface(mCustomFont);
    }
}
