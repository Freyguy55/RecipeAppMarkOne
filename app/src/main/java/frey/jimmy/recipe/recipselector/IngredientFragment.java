package frey.jimmy.recipe.recipselector;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class IngredientFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Ingredient> mIngredientArrayList;
    private ListView mListView;
    private ListAdapter mAdapter;
    private Button mButtonClearList;
    private Button mButtonRemoveSelected;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    public static IngredientFragment newInstance() {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIngredientArrayList = RecipeBook.get(getActivity()).getShoppingList();
        Collections.sort(mIngredientArrayList, new RecipeBook.IngredientComparator());

        mAdapter = new ShoppingListArrayAdapter(getActivity(), R.layout.shopping_list_view_row_layout, mIngredientArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mButtonClearList = (Button) view.findViewById(R.id.buttonShoppingListRemoveAll);
        mButtonClearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredientArrayList.clear();
                updateUi();
                RecipeBook.get(getActivity()).clearShoppingList();
            }
        });
        mButtonRemoveSelected = (Button) view.findViewById(R.id.buttonShoppingListRemoveSelected);
        mButtonRemoveSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

            mListener.onFragmentInteraction(mIngredientArrayList.get(position).toString());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public void updateUi() {
        Collections.sort(mIngredientArrayList, new RecipeBook.IngredientComparator());
        ((ArrayAdapter<Ingredient>) mAdapter).notifyDataSetChanged();
    }

    public void setList(ArrayList<Ingredient> ingredientsToAdd) {
        mIngredientArrayList = ingredientsToAdd;
        updateUi();
    }

    public void removeIngredient(int position) {
        mIngredientArrayList.remove(position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }



}
