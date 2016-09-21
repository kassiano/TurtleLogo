package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.control.TextField;

public class MainController implements Initializable {
	@FXML
	private Canvas canvas;


	GraphicsContext gc;

	double xTartaruga =0, yTartaruga=0;
	double xLapis =0, yLapis=0;
	double angulo =0;

	Image turtle;

	double tHeight, tWidth;

	final static int PASSO=10;


	@FXML TextField txtComando;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		final long startNanoTime = System.nanoTime();

	    turtle = new Image(getClass().getResourceAsStream("turtle.png"));

		gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(1.0);
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


		tHeight = turtle.getHeight();
		tWidth = turtle.getWidth();

		xTartaruga = (canvas.getWidth()/2) - (turtle.getWidth() /2);
		yTartaruga =  (canvas.getHeight()/2) - (turtle.getHeight() / 2);


		yLapis = yTartaruga + (turtle.getHeight() / 2);
		xLapis = xTartaruga;



		new AnimationTimer()
		    {
		        public void handle(long currentNanoTime)
		        {
		            double t = (currentNanoTime - startNanoTime) / 1000000000.0;

		            update();
		            draw();

		        }
		    }.start();
	}

	private void update(){

	}

	private void draw(){
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		if(angulo == 90 ){
			yTartaruga = yLapis;
			xTartaruga = xLapis- (tHeight /2);
		}else if(angulo == 180){
			xTartaruga = xLapis - tWidth;
			yTartaruga = yLapis - (tHeight /2) ;
		}else if(angulo == 270){
			xTartaruga = xLapis- (tWidth /2);
			yTartaruga = yLapis - tHeight;
		}else{
			//angulo 0
			xTartaruga = xLapis;
			yTartaruga = yLapis - (tHeight /2);
		}

		drawRotatedImage(gc, turtle,  angulo,   xTartaruga,   yTartaruga);

		walk();
	}


	private void walk(){

		gc.lineTo(xLapis, yLapis );
		gc.stroke();
		gc.moveTo(xLapis, yLapis );

	}

	 private void rotate(GraphicsContext gc, double angle, double px, double py) {
	        Rotate r = new Rotate(angle, px, py);
	        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	 }


	  private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
	        gc.save(); // saves the current state on stack, including the current transform
	        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
	        gc.drawImage(image, tlpx, tlpy);
	        gc.restore(); // back to original state (before rotation)
	  }

	@FXML public void andarFrente() {

		parafrente(1);
	}

	private void parafrente(int qtdPassos){

		if(qtdPassos ==0) qtdPassos++;

		double distancia = PASSO * qtdPassos;

		if(angulo == 90){
			yTartaruga += distancia;
			yLapis +=distancia;

		}else if(angulo == 180){
			xTartaruga -= distancia;
			xLapis -= distancia;
		}else if(angulo == 270){

			yTartaruga -= distancia;
			yLapis -= distancia;
		}else{
			xTartaruga += distancia;
			xLapis += distancia;
		}
	}

	@FXML public void virarEsquerda() {

		if(angulo == 0)angulo = 270;
		else if(angulo == 270) angulo =180;
		else if(angulo == 180) angulo = 90;
		else if(angulo == 90) angulo = 0;

	}

	@FXML public void virarDireita() {

		if(angulo == 0)angulo = 90;
		else if(angulo == 90) angulo =180;
		else if(angulo == 180) angulo = 270;
		else if(angulo == 270) angulo = 0;

	}

	@FXML public void executar() {


		/*
		 * parafrente
		 * paradireita
		 * paraesquerda
		 */

		String comandoCompleto = txtComando.getText();

		if(!comandoCompleto.isEmpty()){

			String[] comandoVet = comandoCompleto.split(" ");

			if(comandoVet[0].equals("parafrente")){

				String parametro = comandoVet[1];
				int param=1;

				if(parametro.matches("\\d+")){
					param = Integer.parseInt(parametro);
				}

				parafrente(param);
			}

		}

	}

}
