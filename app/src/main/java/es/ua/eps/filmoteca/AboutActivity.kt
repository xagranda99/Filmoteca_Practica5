package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import es.ua.eps.filmoteca.databinding.ActivityAboutBinding
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity

private lateinit var mode : Mode
private lateinit var bindingAboutActivity: ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mode = Mode.valueOf(this.intent.getStringExtra("MODE").toString())
        initUI(this)
    }
}

private fun initUI(context: AboutActivity) {
    when (mode) {
        Mode.Layouts -> initLayouts(context)
        Mode.Compose -> context.setContent { initCompose(context) }
    }
}

private fun initLayouts(context: AboutActivity) {
    bindingAboutActivity = ActivityAboutBinding.inflate(context.layoutInflater)
    context.setContentView(bindingAboutActivity.root)

    bindingAboutActivity.idGoToSiteWeb.setOnClickListener { action_go_to_site_web(context) }
    bindingAboutActivity.idGetSupport.setOnClickListener { action_get_support(context) }
    bindingAboutActivity.idBack.setOnClickListener { action_back(context) }
}

@Composable
private fun initCompose(context: AboutActivity) {
    MaterialTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 120.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.title_author),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_about),
                            contentDescription = "Logo",
                            Modifier
                                .height(235.dp)
                                .width(175.dp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp)
                    ) {
                        Button(onClick = { action_go_to_site_web(context) }) {
                            Text(text = stringResource(R.string.text_go_to_site_web))
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    ) {
                        Button(onClick = { action_get_support(context) }) {
                            Text(text = stringResource(R.string.text_get_support))
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    ) {
                        Button(onClick = { action_back(context) }) {
                            Text(text = stringResource(R.string.text_back))
                        }
                    }
                }
            }
        }
    }
}

private fun action_go_to_site_web(context: AboutActivity) {
    val intent_site_web = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ua.es/"))
    startActivity(context, intent_site_web, null)

}

private fun action_get_support(context: AboutActivity) {
    val intent_get_support = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf("jorgeros98@gmail.com"))
        putExtra(Intent.EXTRA_SUBJECT, "Obtener soporte")
    }

    if (intent_get_support.resolveActivity(context.packageManager) != null) {
        context.startActivity(Intent.createChooser(intent_get_support, "Obtener soporte"))
    }
}

private fun action_back(context: AboutActivity) {
    context.finish()
}

private fun toast_not_yet_implemented(context: AboutActivity) {
    Toast.makeText(context, "Funcionalidad sin implementar", Toast.LENGTH_SHORT).show()
}