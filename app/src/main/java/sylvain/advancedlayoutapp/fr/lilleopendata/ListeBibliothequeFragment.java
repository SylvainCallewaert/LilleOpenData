package sylvain.advancedlayoutapp.fr.lilleopendata;


import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sylvain.advancedlayoutapp.fr.lilleopendata.model.ListeBibli;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListeBibliothequeFragment extends Fragment implements AdapterView.OnItemClickListener{
    private List<ListeBibli> bibliList;
    private ListView bibliListView;


    public ListeBibliothequeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Exécution de la méthode getdatahttp
        getDataFromHttp();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_liste_bibliotheque, container, false);
        bibliListView = view.findViewById(R.id.bibliListView);
        bibliListView.setOnItemClickListener(this);
        return view;
    }

    private void processResponse(String response){
        //Transformation de la réponse json en list de Listebibli
        bibliList =responseToList(response);

        //Convertion de la liste listebibli en tableau de String comportant
        //uniquement le libelle de la bibliotheque
        String[] data = new String[bibliList.size()];
        for(int i=0; i < bibliList.size(); i++){
            data[i] = bibliList.get(i).getLibelle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this.getActivity()
                ,android.R.layout.simple_list_item_1,
                data);

        bibliListView.setAdapter(adapter);

    }

    private void getDataFromHttp() {
        String url = "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=bibliotheques-mel&facet=ville&apikey=6da6c0d952e84957ef2105fa00290cd0ed685b2b78947d0d50b95193";

        // Définition de la requete
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                //Gestionnaire de succes
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("HTTP", response);
                        processResponse(response);
                    }
                },

                //Gestionnaire d'erreur
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP", error.getMessage());
                    }
                }
        );

        //Ajout de la requête à la file d'exécution
        Volley.newRequestQueue(this.getActivity()).add(request);
    }

    /**
     * Conversion d'une réponse json(chaine de caracteres) en une liste de listebibli
     * @param response
     * @return
     */
    private List<ListeBibli> responseToList(String response){
        List<ListeBibli> list = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(response);
            JSONArray jsonbiblis = data.getJSONArray("records");
            JSONObject item;

            for (int i=0;i <jsonbiblis.length();i++){
                item = (JSONObject) jsonbiblis.get(i);
                JSONObject fields = item.getJSONObject("fields");

                //Création d'une nouvelle bibliotheque'

                ListeBibli bibli = new ListeBibli();

                //Hydratation de la bibliotheque
                bibli.setLibelle(fields.getString("libelle"));
                bibli.setAdresse(fields.getString("adresse"));
                bibli.setVille(fields.getString("ville"));

                JSONArray geo = fields.getJSONArray("geometry");

                bibli.setLatitude(geo.getDouble(0));
                bibli.setLongitude(geo.getDouble(1));

                //Ajout de la bibliotheque à la liste
                list.add(bibli);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Récupération de la bibliotheque sur laquelle on vient de cliquer
        ListeBibli  selectedBibli= this.bibliList.get(position);

        //Création d'une intention pour l'affichage de la carte
        Intent mapIntention = new Intent(this.getActivity(),MapsActivity.class);

        //Passage des paramètres à l'intention
        mapIntention.putExtra("libelle", selectedBibli.getLibelle());
        mapIntention.putExtra("latitude", selectedBibli.getLatitude());
        mapIntention.putExtra("longitude", selectedBibli.getLongitude());

        //Affichage de l'activité
        startActivity(mapIntention);
    }
}
