package hu.petrik.pasztorbotond_bevasarlobeadando;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    private List<Termekek> termekList;
    private Context context;

    public Adapter(List<Termekek> termekList, Context context) {
        this.termekList = termekList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return termekList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);

        TextView TVnev = view.findViewById(R.id.TVnev);
        TextView TVar = view.findViewById(R.id.TVar);
        TextView TVmennyiseg = view.findViewById(R.id.TVmennyiseg);
        TextView TVmertekegyseg = view.findViewById(R.id.TVmertekegyseg);
        TextView TVbrutto = view.findViewById(R.id.TVbrutto);
        Log.d("adapter", "here?");
        Log.d("adapter", String.valueOf(termekList.size()));
        Termekek termek = termekList.get(i);
        Log.d("adapter", termek.toString());
        TVnev.setText(termek.getNev());
        TVar.setText(Integer.toString(termek.getEgysegAr()));
        TVmennyiseg.setText(Double.toString(termek.getMennyiseg()));
        TVmertekegyseg.setText(termek.getMertekegyseg());
        TVbrutto.setText(Double.toString(termek.getBruttoAr()));

        return view;
    }
}
