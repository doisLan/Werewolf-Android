package doistres.werewolf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class SelectClassesActivity extends ActionBarActivity {


    // Mensagem a ser recebida
    String message = "";

    // Contador de classes restantes para selecionar
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Deixa fullscreen e remove a barra de status
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Torna a Activity visível
        setContentView(R.layout.activity_select_class);

        // Muda a fonte dos textos para AMATIC
        Typeface amatic = Typeface.createFromAsset(getAssets(), "amatic.ttf");
        TextView text = (TextView) findViewById(R.id.class_select_text);
        text.setTypeface(amatic);
        text = (TextView) findViewById(R.id.text_standart_classes);
        text.setTypeface(amatic);
        text = (TextView) findViewById(R.id.text_optional_classes);
        text.setTypeface(amatic);
        text = (TextView) findViewById(R.id.button_start);
        text.setTypeface(amatic);

        // Recebe mensagem da Activity anterior (quantidade de jogadores)
        Intent intent = getIntent();
        message = intent.getStringExtra("quantity");
        count = Integer.parseInt(message);
        count -= 3;

        // Toca musiquinha
        MainActivity.mMediaPlayer.stop();
        MainActivity.mMediaPlayer = MediaPlayer.create(this, R.raw.catle_run);
        MainActivity.mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        MainActivity.mMediaPlayer.setLooping(true);
        MainActivity.mMediaPlayer.start();

        // Alerta da quantidade de classes opcionais que podem ser escolhidas
        int q = quantityToPick();
        if (q > 1) {
            new AlertDialog.Builder(this)
                    .setMessage("Escolha até " + q + " classes opcionais para adicionar ao jogo.")
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
            new AlertDialog.Builder(this)
                    .setMessage("Escolha até " + q + " classe opcional para adicionar ao jogo.")
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_classpick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Cria um ArrayList com todas as classes_turn selecionadas
    public ArrayList getSelectedClasses(){
        Role r = new Role();
        ArrayList<Role> selected_classes = new ArrayList<>();

        ToggleButton text = (ToggleButton) findViewById(R.id.toggle_lobisomem);
        if(text.isChecked()) selected_classes.add(r.createLobisomem());

        text = (ToggleButton) findViewById(R.id.toggle_campones);
        if(text.isChecked()) selected_classes.add(r.createCampones());

        text = (ToggleButton) findViewById(R.id.toggle_vidente);
        if(text.isChecked()) selected_classes.add(r.createVidente());

        text = (ToggleButton) findViewById(R.id.toggle_cupido);
        if(text.isChecked()) selected_classes.add(r.createCupido());

        text = (ToggleButton) findViewById(R.id.toggle_cacador);
        if(text.isChecked()) selected_classes.add(r.createCacador());

        text = (ToggleButton) findViewById(R.id.toggle_garotinha);
        if(text.isChecked()) selected_classes.add(r.createGarotinha());

        text = (ToggleButton) findViewById(R.id.toggle_bruxa);
        if(text.isChecked()) selected_classes.add(r.createBruxa());

        text = (ToggleButton) findViewById(R.id.toggle_lobisomem_alfa);
        if(text.isChecked()) selected_classes.add(r.createLobisomemAlfa());

        text = (ToggleButton) findViewById(R.id.toggle_traidor);
        if(text.isChecked()) selected_classes.add(r.createTraidor());

        text = (ToggleButton) findViewById(R.id.toggle_silenciador);
        if(text.isChecked()) selected_classes.add(r.createSilenciador());

        text = (ToggleButton) findViewById(R.id.toggle_mago);
        if(text.isChecked()) selected_classes.add(r.createMago());

        return selected_classes;
    }

    // Seta a quantidade de camponeses e lobisomens
    public ArrayList getSelectedClassesQuantity(){
        int quantity = Integer.parseInt(message);
        ArrayList<Role> classes = getSelectedClasses();
        Role r = new Role();

        if (quantity >= 10){
            classes.add(r.createLobisomem());
        }

        if (quantity >= 20){
            classes.add(r.createLobisomem());
        }

        for (int i = classes.size(); i < quantity; i++){
            classes.add(r.createCampones());
        }
        return classes;
    }

    // Retorna a quantidade de classes opcionais que podem ser selecionadas
    public int quantityToPick(){
        int quantity = Integer.parseInt(message);

        // 3 vem de 1 lobisomem, 1 camponês e 1 vidente obrigatórios
        if (quantity < 10){
            return ((quantity-2)/2);
        }

        // 4 vem de 2 lobisomem, 1 camponês e 1 vidente obrigatórios
        if (quantity >= 10){
            return ((quantity-3)/2);
        }

        // 5 vem de 3 lobisomem, 1 camponês e 1 vidente obrigatórios
        if (quantity >= 20){
            return ((quantity-4)/2);
        }

        return 0;
    }

    // Vai para a prox Activity
    public void goToGameSetupConfirmationActivity(View view) {

        int quantity_selected = getSelectedClasses().size()-3;
        int quantity_to_select = quantityToPick();

        if (quantity_selected <= quantity_to_select) {

            // Cria mensagem para enviar para prox Activity (classes_turn selecionadas)
            ArrayList roles = getSelectedClassesQuantity();

            // Cria um intent da prox Activity
            Intent intent = new Intent(this, GameSetupConfirmationActivity.class);
            intent.putExtra("quantity", message);
            intent.putParcelableArrayListExtra("roles", roles);

            Button button = (Button) findViewById(R.id.button_start);
            button.setTextColor(Color.GRAY);

            // Inicia prox Activity
            startActivity(intent);
            this.finish();
        }

        else {
            int q = quantityToPick();
            if (q > 1) {
                new AlertDialog.Builder(this)
                        .setMessage("Você só pode escolher " + q + " classes opcionais para a quantidade de jogadores indicada!")
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else new AlertDialog.Builder(this)
                    .setMessage("Você só pode escolher " + q + " classe opcional para a quantidade de jogadores indicada!")
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }

}
