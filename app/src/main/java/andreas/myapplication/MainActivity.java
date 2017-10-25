package andreas.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import andreas.faceinhole.Faceinhole;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Faceinhole faceinhole =(Faceinhole) findViewById(R.id.view);
        faceinhole.setImages(getResources().getDrawable(R.drawable.face),getResources().getDrawable(R.drawable.body));
        Button t=(Button)findViewById(R.id.button);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView r=(ImageView) findViewById(R.id.imageView3);
                r.setImageBitmap(faceinhole.mergeImages());
            }
        });
    }
}
