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

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initializeTableLayout(View v) {
        TableRow flavorRow = (TableRow) v.findViewById(R.id.flavorRow);
        TextView textView = (TextView) flavorRow.getChildAt(0);
        textView.setText(getString(R.string.advanced_search_flavor_category));
        Spinner flavorSpinner = (Spinner) flavorRow.getChildAt(1);
        ArrayList<String> spinnerOptions = new ArrayList<>();
        spinnerOptions.add("All");
        spinnerOptions.add("Sweet");
        spinnerOptions.add("Savory");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavorSpinner.setAdapter(adapter);
    }
}
