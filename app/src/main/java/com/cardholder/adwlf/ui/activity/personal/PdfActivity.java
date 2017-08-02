package com.cardholder.adwlf.ui.activity.personal;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cardholder.adwlf.AppContext;
import com.cardholder.adwlf.R;
import com.cardholder.adwlf.bean.request.UpdateMsg;
import com.cardholder.adwlf.control.ScreenControl;
import com.cardholder.adwlf.ui.activity.basic.SuperActivity;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 创建者：杨操
 * 时间：2016/5/11
 * 功能描述： 查看Pdf文件
 */
public class PdfActivity extends SuperActivity {
    public static final String PDFURL = "pdf_url";
    @InjectView(R.id.pdf_menu)
    ImageView pdfMenu;
    @InjectView(R.id.pdf_webview)
    WebView pdfWebview;
    private String pdfurl;
    private PopupWindow popupWindow;
    private DownloadChangeObserver downloadChangeObserver;
    private String pdfname="aaaa";
    private Handler handler;
    private boolean GOPDF;

    @Override
    public void onCreateDataForView() {
        String stringExtra = getIntent().getStringExtra(PDFURL);
        if (!TextUtils.isEmpty(stringExtra)) {
            pdfurl = stringExtra;
        }
        setTitle(R.id.red_pdf_title, "PDF详情");
        DownloadManager downloadManager;
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/"+pdfname+".pdf");
        if(file.exists()){
            goReadPdf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/" + pdfname + ".pdf");
        }else {
            down(downloadManager, pdfurl);
        }

    }

    @Override
    public void setThisContentView() {
        setContentView(R.layout.activity_pdf);
    }

    @OnClick({R.id.pdf_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pdf_menu:
                    showPupMenu();
                break;
        }
    }
    private void showPupMenu(){
        if(popupWindow==null){
            View inflate = LayoutInflater.from(this).inflate(R.layout.pdf_menu, null);
            TextView copy= (TextView) inflate.findViewById(R.id.menu_copy_url);
            TextView down= (TextView) inflate.findViewById(R.id.menu_down);
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    onClickCopy();
                }
            });
            down.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    DownloadManager downloadManager;
                    downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/"+pdfname+".pdf");
                    if(file.exists()){
                        showDownHit(downloadManager,pdfurl);
                    }else {
                        down(downloadManager, pdfurl);
                    }
                }
            });
            popupWindow = new PopupWindow(inflate,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setWidth((int)(new ScreenControl().getscreenWide()*0.3));
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
        }

        popupWindow.showAtLocation(pdfWebview, Gravity.TOP | Gravity.RIGHT, 4, (int) (new ScreenControl().getscreenHigh()-pdfWebview.getHeight()));
    }
    public void onClickCopy() {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(pdfurl);
        Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
    }


    private void down(DownloadManager downloadManager, String pdfurl) {
        handler=new Handler();
        Uri uri = Uri.parse(pdfurl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/");
        if(!file.exists()){
            file.mkdirs();
        }
        File filee=new File(file.getAbsolutePath()+"/"+pdfname + ".pdf");
        request.setDestinationUri(Uri.fromFile(filee));
        long reference = downloadManager.enqueue(request);
        downloadChangeObserver = new DownloadChangeObserver(null,this);
        this.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);
        AppContext.getInstance().setDownId(reference);
    }

    class DownloadChangeObserver extends ContentObserver {

        private DownloadManager.Query query;
        private ProgressDialog pbarDialog;
        public DownloadChangeObserver(Handler handler,Context context) {
            super(handler);
            pbarDialog = new ProgressDialog( context );
            pbarDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pbarDialog.setMessage("下载进度...");
            pbarDialog.setCancelable(true);
            pbarDialog.show();
        }

        @Override

        public void onChange(boolean selfChange) {
            if (null == query) {
                query = new DownloadManager.Query();
                query.setFilterById(AppContext.getInstance().getDownId());
            }
            Cursor c = ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (c != null && c.moveToFirst()) {
                int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
                int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
                int fileSizeIdx =
                        c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int bytesDLIdx =
                        c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                String title = c.getString(titleIdx);
                int fileSize = c.getInt(fileSizeIdx);
                int bytesDL = c.getInt(bytesDLIdx);
                StringBuilder sb = new StringBuilder();
                sb.append(title).append("\n");
                sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);
                System.out.println(sb);
                pbarDialog.setProgress(bytesDL * 100 / fileSize);
                if(!selfChange&&bytesDL==fileSize){
                    pbarDialog.dismiss();
                    if(!GOPDF){
                        GOPDF=true;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PdfActivity.this,"文件已下载到"+Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/"+pdfname+".pdf",Toast.LENGTH_LONG).show();
                                goReadPdf(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eshare/pdf/"+pdfname+".pdf");
                            }
                        });
                    }



                }
            }
            c.close();
        }


    }
    private void showDownHit(final DownloadManager downloadManager, final String pdfurl){
        AlertDialog.Builder b = new AlertDialog.Builder(this).setTitle("文件已经下载成功")
                .setMessage("如果手动删除了文件请选择重新下载").setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("重新下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        down(downloadManager, pdfurl);
                        dialog.dismiss();
                    }
                });
        b.create();
        b.show();
    }
    private void queryDownloadStatus(final DownloadManager dowanloadmanager, final Long lastDownloadId, final UpdateMsg data) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(lastDownloadId);
        Cursor c = dowanloadmanager.query(query);
        if (c != null && c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                    Toast.makeText(PdfActivity.this, "文件正在下载", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    break;
                case DownloadManager.STATUS_FAILED:
                    dowanloadmanager.remove(lastDownloadId);
                    down(dowanloadmanager, pdfurl);
                    break;
            }
        } else {
            down(dowanloadmanager, pdfurl);
        }
    }
    private void goReadPdf(String path) {
        /*Uri uri=Uri.parse(path);
        Intent intent = new Intent(PdfActivity.this, MuPDFActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);

        //if document protected with password
        intent.putExtra("password", "encrypted PDF password");

        //if you need highlight link boxes
        intent.putExtra("linkhighlight", true);

        //if you don't need device sleep on reading document
        intent.putExtra("idleenabled", false);

        //set true value for horizontal page scrolling, false value for vertical page scrolling
        intent.putExtra("horizontalscrolling", true);

        //document name
        intent.putExtra("docname", pdfname);

        startActivity(intent);
        finish();*/
    }

}
