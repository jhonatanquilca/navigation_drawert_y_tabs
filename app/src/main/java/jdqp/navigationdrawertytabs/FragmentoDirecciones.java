package jdqp.navigationdrawertytabs;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jdqp.navigationdrawertytabs.modelo.AdaptadorDirecciones;
import jdqp.navigationdrawertytabs.ui.DecoracionLineaDivisoria;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoDirecciones extends Fragment {

    private LinearLayoutManager linearLayout;

    public FragmentoDirecciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmento_direcciones, container, false);

        RecyclerView reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        linearLayout = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayout);

        AdaptadorDirecciones adaptador = new AdaptadorDirecciones();
        reciclador.setAdapter(adaptador);
        reciclador.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));

        return view;
    }


}
