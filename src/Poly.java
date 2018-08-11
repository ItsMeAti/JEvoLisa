import java.awt.Color;
import java.awt.Graphics;

public class Poly {

	int[] x, y;
	int r, g, b, a;
	int n;

	public Poly(int max) {
		this.n = Util.getRandom(3, max + 1);
		this.x = new int[n];
		this.y = new int[n];

		for (int i = 0; i < n; i++) {
			this.x[i] = Util.getRandom(0, GUI.tWidth + 1);
			this.y[i] = Util.getRandom(0, GUI.tHeight + 1);
		}

		this.r = Util.getRandom(0, 256);
		this.g = Util.getRandom(0, 256);
		this.b = Util.getRandom(0, 256);
		this.a = Util.getRandom(0, 256);
	}

	public Poly(Poly p) {
		this.n = p.n;
		this.x = new int[n];
		this.y = new int[n];

		for (int i = 0; i < n; i++) {
			this.x[i] = p.x[i];
			this.y[i] = p.y[i];
		}

		this.r = p.r;
		this.g = p.g;
		this.b = p.b;
		this.a = p.a;
	}

	public Poly softMut(float mutPerc) {
		int rand = Util.getRandom(0, 2);
		int mC = Math.round(256 * mutPerc);
		int vertex = Util.getRandom(0, this.n);
		switch (Util.getRandom(0, 6)) {
		case 0:
			int mXperc = Math.round((GUI.tWidth + 1) * mutPerc);
			int mX = Util.getRandom(0, mXperc + 1);
			if (rand == 0) {
				if (this.x[vertex] + mX < GUI.tWidth) {
					this.x[vertex] += mX;
				} else {
					this.x[vertex] += 0;
				}
			} else if (rand == 1) {
				if (this.x[vertex] - mX > 0) {
					this.x[vertex] -= mX;
				} else {
					this.x[vertex] -= 0;
				}
			}
			break;
		case 1:
			int mYperc = Math.round((GUI.tHeight + 1) * mutPerc);
			int mY = Util.getRandom(0, mYperc + 1);
			if (rand == 0) {
				if (this.y[vertex] + mY < GUI.tHeight) {
					this.y[vertex] += mY;
				} else {
					this.y[vertex] += 0;
				}
			} else if (rand == 1) {
				if (this.y[vertex] - mY > 0) {
					this.y[vertex] -= mY;
				} else {
					this.y[vertex] -= 0;
				}
			}
			break;
		case 2:
			if (rand == 0) {
				if(this.r+mC < 255){
					this.r += Util.getRandom(0, mC);
				}else{
					this.r +=0;
				}
			} else if (rand == 1) {
				if(this.r - mC > 0){
					this.r -= Util.getRandom(0, mC);
				}else{
					this.r -= 0;
				}
			}
			break;
		case 3:
			if (rand == 0) {
				if(this.g+mC < 255){
					this.g += Util.getRandom(0, mC);
				}else{
					this.g +=0;
				}
			} else if (rand == 1) {
				if(this.g - mC > 0){
					this.g -= Util.getRandom(0, mC);
				}else{
					this.g -= 0;
				}
			}
			break;
		case 4:
			if (rand == 0) {
				if(this.b+mC < 255){
					this.b += Util.getRandom(0, mC);
				}else{
					this.b+=0;
				}
			} else if (rand == 1) {
				if(this.b - mC > 0){
					this.b -= Util.getRandom(0, mC);
				}else{
					this.b -= 0;
				}
			}
			break;
		case 5:
			if (rand == 0) {
				if(this.a+mC < 255){
					this.a += Util.getRandom(0, mC);
				}else{
					this.a +=0;
				}
			} else if (rand == 1) {
				if(this.a - mC > 0){
					this.a -= Util.getRandom(0, mC);
				}else{
					this.a -= 0;
				}
			}
			break;
		}
		return this;
	}

	public Poly mediumMut() {
		switch (Util.getRandom(0, 6)) {
		case 0:
			this.x[Util.getRandom(0, this.n)] = Util.getRandom(0, GUI.tWidth + 1);
			break;
		case 1:
			this.y[Util.getRandom(0, this.n)] = Util.getRandom(0, GUI.tHeight + 1);
			break;
		case 2:
			this.r = Util.getRandom(0, 256);
			break;
		case 3:
			this.g = Util.getRandom(0, 256);
			break;
		case 4:
			this.b = Util.getRandom(0, 256);
			break;
		case 5:
			this.a = Util.getRandom(0, 256);
			break;
		}
		return this;
	}

	public Poly hardMut() {
		int vert = Util.getRandom(0, this.n);
		int color = Util.getRandom(0, 3);
		this.x[vert] = Util.getRandom(0, GUI.tWidth + 1);
		this.y[vert] = Util.getRandom(0, GUI.tHeight + 1);
		this.a = Util.getRandom(0, 256);
		switch (color) {
		case 0:
			this.r = Util.getRandom(0, 256);
			break;
		case 1:
			this.g = Util.getRandom(0, 256);
			break;
		case 2:
			this.b = Util.getRandom(0, 256);
			break;
		}
		return this;
	}

	public void drawPolygon(Graphics g1) {
		g1.setColor(new Color(r, g, b, a));
		g1.fillPolygon(x, y, n);
	}

}
