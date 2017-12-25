package in.codepeaker.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import in.codepeaker.bakingapp.R;
import in.codepeaker.bakingapp.adapters.IngredientAdapter;
import in.codepeaker.bakingapp.adapters.StepsAdapter;
import in.codepeaker.bakingapp.constant.Constant;
import in.codepeaker.bakingapp.model.BakingModel;
import in.codepeaker.bakingapp.utils.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private ArrayList<BakingModel.IngredientsBean> ingredientsBeans;
    private ArrayList<BakingModel.StepsBean> stepsBeans;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        initScreen(view);

        if (savedInstanceState == null)
            initAction();
        else {
            ingredientsBeans = savedInstanceState.getParcelableArrayList(Constant.ingredients);
            stepsBeans = savedInstanceState.getParcelableArrayList(Constant.step);
            setRecyclerView(ingredientsBeans, stepsBeans);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constant.step, stepsBeans);
        outState.putParcelableArrayList(Constant.ingredients, ingredientsBeans);
    }

    private void initScreen(View view) {
        ingredientsRecyclerView = view.findViewById(R.id.ingred_recyclerview);
        stepsRecyclerView = view.findViewById(R.id.steps_recyclerview);
    }

    private void initAction() {
        Bundle data = null;
        if (getActivity() != null)
            data = getActivity().getIntent().getExtras();

        if (data == null)
            return;

        if (data.get(Constant.fromWidget) != null && (boolean) data.get(Constant.fromWidget)) {

            int recipeposition = (int) data.get(Constant.fillInIntentRecipePosition);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<BakingModel>>() {
            }.getType();
            String bakingModelString = AppUtils.getStringpreferences(getActivity(), Constant.bakingmodel);
            ArrayList<BakingModel> bakingModels = gson.fromJson(bakingModelString, type);
            ingredientsBeans = bakingModels.get(recipeposition).getIngredients();
            stepsBeans = bakingModels.get(recipeposition).getSteps();

            setRecyclerView(ingredientsBeans, stepsBeans);
            getActivity().setTitle(bakingModels.get(recipeposition).getName());
            return;
        }

        getActivity().setTitle(data.getString(Constant.recipeName));

        ingredientsBeans = data.getParcelableArrayList(Constant.ingredients);

        stepsBeans = data.getParcelableArrayList(Constant.stepsBean);

        setRecyclerView(ingredientsBeans, stepsBeans);

    }

    private void setRecyclerView(ArrayList<BakingModel.IngredientsBean> ingredientsBeans
            , ArrayList<BakingModel.StepsBean> stepsBeans) {

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity()
                , ingredientsBeans);
        ingredientsRecyclerView.setAdapter(ingredientAdapter);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StepsAdapter stepsAdapter = new StepsAdapter(getActivity()
                , stepsBeans, mListener);
        stepsRecyclerView.setAdapter(stepsAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(position);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }
}
