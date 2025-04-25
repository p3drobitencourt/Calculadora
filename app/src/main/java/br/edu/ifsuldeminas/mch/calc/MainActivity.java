package br.edu.ifsuldeminas.mch.calc;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private Button buttonVirgula, buttonZero, buttonUm, buttonDois, buttonTres, buttonQuatro, buttonCinco, buttonSeis, buttonSete, buttonOito, buttonNove;
    private Button buttonPorcentagem, buttonDivisao, buttonMultiplicacao, buttonAdicao, buttonSubtracao;
    private Button buttonIgual, buttonReset, buttonD;
    private TextView textViewResultado, textViewUltimaExpressao;
    private String expressaoAnterior = "";
    private String expressao = "";

    public boolean verificaSeASinalIgualLadoaLado(String texto) {
        if (texto.isEmpty()) return false;
        char ultimo = texto.charAt(texto.length() - 1);
        return ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/' || ultimo == '%';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonVirgula = findViewById(R.id.buttonVirgulaID);
        buttonZero = findViewById(R.id.buttonZeroID);
        buttonUm = findViewById(R.id.buttonUmID);
        buttonDois = findViewById(R.id.buttonDoisID);
        buttonTres = findViewById(R.id.buttonTresID);
        buttonQuatro = findViewById(R.id.buttonQuatroID);
        buttonCinco = findViewById(R.id.buttonCincoID);
        buttonSeis = findViewById(R.id.buttonSeisID);
        buttonSete = findViewById(R.id.buttonSeteID);
        buttonOito = findViewById(R.id.buttonOitoID);
        buttonNove = findViewById(R.id.buttonNoveID);

        buttonDivisao = findViewById(R.id.buttonDivisaoID);
        buttonAdicao = findViewById(R.id.buttonSomaID);
        buttonSubtracao = findViewById(R.id.buttonSubtracaoID);
        buttonPorcentagem = findViewById(R.id.buttonPorcentoID);
        buttonMultiplicacao = findViewById(R.id.buttonMultiplicacaoID);

        buttonD = findViewById(R.id.buttonDeleteID);
        buttonReset = findViewById(R.id.buttonResetID);
        buttonIgual = findViewById(R.id.buttonIgualID);

        textViewResultado = findViewById(R.id.textViewResultadoID);
        textViewUltimaExpressao = findViewById(R.id.textViewUltimaExpressaoID);

        View.OnClickListener numberClickListener = v -> {
            Button button = (Button) v;
            expressao += button.getText().toString();
            textViewResultado.setText(expressao);
        };

        buttonZero.setOnClickListener(numberClickListener);
        buttonUm.setOnClickListener(numberClickListener);
        buttonDois.setOnClickListener(numberClickListener);
        buttonTres.setOnClickListener(numberClickListener);
        buttonQuatro.setOnClickListener(numberClickListener);
        buttonCinco.setOnClickListener(numberClickListener);
        buttonSeis.setOnClickListener(numberClickListener);
        buttonSete.setOnClickListener(numberClickListener);
        buttonOito.setOnClickListener(numberClickListener);
        buttonNove.setOnClickListener(numberClickListener);
        buttonVirgula.setOnClickListener(numberClickListener);

        buttonDivisao.setOnClickListener(v -> addOperator("/"));
        buttonAdicao.setOnClickListener(v -> addOperator("+"));
        buttonSubtracao.setOnClickListener(v -> addOperator("-"));
        buttonMultiplicacao.setOnClickListener(v -> addOperator("*"));
        buttonPorcentagem.setOnClickListener(v -> {
            if (!expressao.isEmpty()) {
                expressao += "/100"; // Adiciona a divisão por 100 à expressão
                textViewResultado.setText(expressao);
            }
        });

        buttonD.setOnClickListener(v -> {
            if (!expressao.isEmpty()) {
                expressao = expressao.substring(0, expressao.length() - 1);
            }
            textViewResultado.setText(expressao.isEmpty() ? "0" : expressao);
        });

        buttonReset.setOnClickListener(v -> {
            expressao = "";
            expressaoAnterior = "";
            textViewResultado.setText("0");
            textViewUltimaExpressao.setText("");
        });

        buttonIgual.setOnClickListener(v -> {
            String expressaoParaCalculo = expressao.replace(",", "."); // Substitui vírgula por ponto
            try {
                Calculable calc = new ExpressionBuilder(expressaoParaCalculo).build();
                double resultado = calc.calculate();
                expressaoAnterior = expressao;
                expressao = String.valueOf(resultado);
                textViewUltimaExpressao.setText(expressaoAnterior);
                textViewResultado.setText(expressao);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Expressão inválida", Toast.LENGTH_SHORT).show();
                expressao = "";
                expressaoAnterior = "";
                textViewResultado.setText("0");
                textViewUltimaExpressao.setText("");
            }
        });
    }

    private void addOperator(String operador) {
        if (!verificaSeASinalIgualLadoaLado(expressao)) {
            expressao += operador;
            textViewResultado.setText(expressao);
        }
    }
}