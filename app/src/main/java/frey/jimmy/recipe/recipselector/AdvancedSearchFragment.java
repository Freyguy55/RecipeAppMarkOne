package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by James on 5/18/2015.
 */
public class AdvancedSearchFragment extends Fragment {

    public static AdvancedSearchFragment createInstance() {
        AdvancedSearchFragment fragment = new AdvancedSearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        initializeTableLayout(v);

        return v;
    }

    private void initializeTableLayout(View v) {
        TableRow flavorRow = (TableRow) v.findViewById(R.id.flavorRow);
        TextView flavorTextView = (TextView) flavorRow.getChildAt(0);
        flavorTextView.setText(getString(R.string.advanced_search_flavor_category));
        Spinner flavorSpinner = (Spinner) flavorRow.getChildAt(1);
        ArrayList<String> flavorSpinnerOptions = new ArrayList<>();
        flavorSpinnerOptions.add("All");
        flavorSpinnerOptions.add("Sweet");
        flavorSpinnerOptions.add("Savory");
        ArrayAdapter<String> flavorAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,flavorSpinnerOptions);
        flavorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavorSpinner.setAdapter(flavorAdapter);

        TableRow heavinessRow = (TableRow) v.findViewById(R.id.flavorRow);
        TextView heavinessTextView = (TextView) flavorRow.getChildAt(0);
        heavinessTextView.setText(getString(R.string.advanced_search_flavor_category));
        Spinner heavinessSpinner = (Spinner) flavorRow.getChildAt(1);
        ArrayList<String> heavySpinnerOptions = new ArrayList<>();
        heavySpinnerOptions.add("All");
        heavySpinnerOptions.add("Light");
        heavySpinnerOptions.add("Heavy");
        ArrayAdapter<String> heavinessAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,heavySpinnerOptions);
        heavinessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heavinessSpinner.setAdapter(heavinessAdapter);
    }
}
