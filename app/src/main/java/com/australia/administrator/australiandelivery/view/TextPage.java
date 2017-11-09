package com.australia.administrator.australiandelivery.view;

import android.content.Context;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Administrator on 2017/5/26.
 */

public class TextPage extends EditText {
    private Context context;
    public TextPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public TextPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public TextPage(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return super.getDefaultMovementMethod();
    }

    @Override
    public boolean getDefaultEditable() {
        return false;
    }

    // 点击menu中的选定item的具体处理方法，捕捉点击文本复制、剪切等按钮的动作
// 如果要在点击复制按钮之后取消该textview的cursor可见性的具体监听写在这里
    @Override
    public boolean onTextContextMenuItem(int id) {
        setCursorVisible(true);
        boolean flag;
        if (id != android.R.id.switchInputMethod) {
            flag = super.onTextContextMenuItem(id);
        } else {
            setCursorVisible(false);
            return false;
        }
        if (id == android.R.id.copy) {
            setCursorVisible(false);
            cursorStart = -1;
        }
        return flag;
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
        if (isInputMethodTarget()) {
            menu.removeItem(android.R.id.switchInputMethod);
        }
    }

    // textview的点击捕捉
// 如果双击textview选中了具体文字，则使cursor可见
    int cursorStart = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        // 先隐藏键盘
        ((InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (event.getAction() == MotionEvent.ACTION_DOWN && hasSelection()) {
            if (cursorStart == -1) {// 由于点击选中文字后，再点击其他位置，第一次点击时显示的hasSelection依然为true，这样一来cursor会依然还在，为了避免这种情况，我这里多对selectionStart进行了一次验证
                setCursorVisible(true);
                cursorStart = getSelectionStart();
            } else {
                setCursorVisible(false);
                cursorStart = -1;

            }
        }
        return flag;
    }

    // 当按返回键取消文字复制时，使cursor再次不可见
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = super.onKeyDown(keyCode, event);

        setCursorVisible(false);
        cursorStart = -1;
        return flag;
    }

}
