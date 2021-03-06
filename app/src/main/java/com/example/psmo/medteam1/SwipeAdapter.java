package com.example.psmo.medteam1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by przem on 21.11.2016.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {
    private int parentID;
    private String xmlfile;
    private String algName;
    private Context c;
    private int nextelement;
    private int count_successors;
    private int image;
    public SwipeAdapter(FragmentManager fm, Context c,int parentID, String xmlfile, String algName)
    {
        super(fm);
        this.c = c;
        this.parentID=parentID;
        this.xmlfile = xmlfile;
        this.algName = algName;
    }

    @Override
    public Fragment getItem(int position)
    {

        List<AlgorithmElement> list = null;
        String opis="";
        String krok="";
        String extrainfo="";
        InputStream is = null;

        try
        {
            is = c.getAssets().open(xmlfile);
        } catch (IOException e1)
            {
                e1.printStackTrace();
            }

        try
        {
            list = new XmlParser().parse(is);
            opis = list.get(parentID).getSuccessors().get(position).getDescription();
            nextelement=list.get(parentID).getSuccessors().get(position).getSucId();
            count_successors=list.get(parentID).getSuccessors().size();
            krok=list.get(nextelement-1).getDescription();
            image= c.getResources().getIdentifier(list.get(nextelement-1).getB64Image(), "drawable", c.getPackageName());
            extrainfo=list.get(nextelement-1).getExtraDescription();
        } catch (XmlPullParserException e1)
            {
                 e1.printStackTrace();
            } catch (IOException e1)
                {
                    e1.printStackTrace();
                }

        Fragment fragment= new PageFragment();
        Bundle bundle =new Bundle();
        bundle.putInt("position",nextelement-1);
        bundle.putInt("count_successors",count_successors);
        bundle.putString("opis",opis);
        bundle.putString("krok",krok);
        bundle.putString("extrainfo",extrainfo);
        bundle.putInt("image",image);
        bundle.putString("xml", xmlfile);
        bundle.putString("algName", algName);
        String prev="";
        String next="";
        if(position+1<count_successors)
            next="1";
        if(position!=0)
            prev="1";
        bundle.putString("prev",prev);
        bundle.putString("next",next);
        fragment.setArguments(bundle);

        return fragment;
    }




    @Override
    public int getCount() {
        int ile = 0;
        List<AlgorithmElement> list = null;



        InputStream is = null;
        try
        {
            is = c.getAssets().open(xmlfile);
        } catch (IOException e1)
            {
                e1.printStackTrace();
            }

        try
        {
            list = new XmlParser().parse(is);
            ile = list.get(parentID).getSuccessors().size();
        } catch (XmlPullParserException e1)
            {
                e1.printStackTrace();
            } catch (IOException e1)
                {
                    e1.printStackTrace();
                }

        return ile;
    }
}
