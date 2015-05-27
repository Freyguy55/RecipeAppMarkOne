package frey.jimmy.recipe.recipselector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

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
        //Flavor selection
        TableRow flavorRow = (TableRow) v.findViewById(R.id.flavorRow);
        TextView flavorTextView = (TextView) flavorRow.getChildAt(0);
        flavorTextView.setText(getString(R.string.advanced_search_flavor_category));
        Spinner flavorSpinner = (Spinner) flavorRow.getChildAt(1);
        ArrayList<String> flavorSpinnerOptions = RecipeBook.get(getActivity()).getSpinnerList(0);
        ArrayAdapter<String> flavorAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,flavorSpinnerOptions);
        flavorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavorSpinner.setAdapter(flavorAdapter);

        //Lightness selection
        TableRow heavinessRow = (TableRow) v.findViewById(R.id.fillingRow);
        TextView heavinessTextView = (TextView) heavinessRow.getChildAt(0);
        heavinessTextView.setText(getString(R.string.light_heavy_category_text));
        Spinner heavinessSpinner = (Spinner) heavinessRow.getChildAt(1);
        ArrayList<String> heavySpinnerOptions = RecipeBook.get(getActivity()).getSpinnerList(1);
        ArrayAdapter<String> heavinessAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,heavySpinnerOptions);
        heavinessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heavinessSpinner.setAdapter(heavinessAdapter);

        //Region selection
        TableRow regionRow = (TableRow) v.findViewById(R.id.categoryRow);
        TextView regionTextView = (TextView) regionRow.getChildAt(0);
        regionTextView.setText(getString(R.string.region_category_text));
        Spinner regionSpinner = (Spinner) regionRow.getChildAt(1);
        ArrayList<String> regionSpinnerOptions = RecipeBook.get(getActivity()).getSpinnerList(2);
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,regionSpinnerOptions);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

        //Search button
        Button buttonGo = (Button)v.findViewById(R.id.buttonAdvancedSearchGo);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<UUID> filteredList = RecipeBook.get(getActivity()).getFilteredRecipeIds(true, true);
                if (filteredList == null) {
                    Toast.makeText(getActivity(), "No recipes match your query.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
