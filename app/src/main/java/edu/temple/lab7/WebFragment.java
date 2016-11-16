package edu.temple.lab7;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment{

    //public field
    public WebView myWebView;
    private String theURL;

    public WebFragment(){
        //required empty public constructor
    }

    @Override public void onAttach(Activity c){
        super.onAttach(c);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_web, container, false);

        //assign variable and reference to the webview fragment
        myWebView = (WebView) v.findViewById(R.id.webview);

        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());

        //if user pressed back or forward tab button load the current url
        if(theURL != null){
            myWebView.loadUrl(theURL);
        }

        return v;
    }

    //public method to allow url to passed from url box to web fragment
    public void setURL(String url){
        theURL = url;
        myWebView.loadUrl(theURL);
    }

}
