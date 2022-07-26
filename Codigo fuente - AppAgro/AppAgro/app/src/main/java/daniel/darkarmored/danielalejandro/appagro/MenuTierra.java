package daniel.darkarmored.danielalejandro.appagro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class MenuTierra extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //mio
    ImageButton botonFranco;
    ImageButton botonArcilloso;
    ImageButton botonArenoso;
    ImageButton botonFrancoArenoso;
    ImageButton botonFrancoArcilloso;
    ImageButton botonFrancoLimoso;
    ImageButton botonFrancoArcillosoArenoso;

    private OnFragmentInteractionListener mListener;

    public MenuTierra() {
        // Required empty public constructor
    }

    public static MenuTierra newInstance(String param1, String param2) {
        MenuTierra fragment = new MenuTierra();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_tierra, container, false);

        botonFranco = (ImageButton) view.findViewById(R.id.Franco);
        botonFranco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), Franco.class);
                startActivity(SE);


            }
        });

        botonArcilloso = (ImageButton) view.findViewById(R.id.Arcilloso);
        botonArcilloso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), Arcilloso.class);
                startActivity(SE);
            }
        });

        botonArenoso= (ImageButton) view.findViewById(R.id.Arenoso);
        botonArenoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), Arenoso.class);
                startActivity(SE);
            }
        });

        botonFrancoArenoso= (ImageButton) view.findViewById(R.id.FrancoArenoso);
        botonFrancoArenoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), FrancoArenoso.class);
                startActivity(SE);
            }
        });

        botonFrancoArcilloso= (ImageButton) view.findViewById(R.id.FrancoArcilloso);
        botonFrancoArcilloso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), FrancoArcilloso.class);
                startActivity(SE);
            }
        });

        botonFrancoLimoso= (ImageButton) view.findViewById(R.id.FrancoLimoso);
        botonFrancoLimoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), FrancoLimoso.class);
                startActivity(SE);
            }
        });

        botonFrancoArcillosoArenoso= (ImageButton) view.findViewById(R.id.FrancoArcillosoArenoso);
        botonFrancoArcillosoArenoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SE = new Intent (getActivity(), FrancoArcillosoArenoso.class);
                startActivity(SE);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
