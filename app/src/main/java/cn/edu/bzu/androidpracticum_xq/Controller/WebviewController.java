package cn.edu.bzu.androidpracticum_xq.Controller;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.edu.bzu.androidpracticum_xq.R;

public class WebviewController {

    private Context context;

    public WebviewController(Context context) {
        this.context = context;
    }
    public View getView(String url){
        //获取子布局
        View view =View.inflate(context,R.layout.layout,null);
        //从子布局里查找WebView控件并初始化
        WebView webView = view.findViewById(R.id.wv);
        //使用loadUrl加载网页
        webView.loadUrl(url);
        //设置WebView允许执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 禁止系统浏览器打开页面
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return  view;
    }
}
