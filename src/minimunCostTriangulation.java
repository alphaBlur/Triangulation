import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class minimunCostTriangulation {
	static int n= 100;
	static double table[][] = new double[n][n];
	static int vertices[][] = new int[2*n-3][2];
	static int ind=0;
	static ArrayList<Point> ps = new ArrayList<Point>();
	public static void main(String[] args){
			
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		for(int i=0; i<n; ++i){
			int n1=sc.nextInt();
			int n2=sc.nextInt();
			Point p = new Point();
			p.x=n1;p.y=n2;
			ps.add(p);
		}
		
		for(int i=0; i<n; ++i){
			System.out.println("Point " + (i+1) + "=" + ps.get(i).x + "," + ps.get(i).y + "\n");
		}
		sc.close();
		double minCost = minCostTriangulation(ps,n);
		System.out.println("The minimum cost is " + minCost);
	}
	
	private static double minCostTriangulation(ArrayList<Point> ps, int n) {
		if(n<3)
			return 0;
		
		int valK[][] = new int[n][n];
		
		for (int gap = 2; gap < n; gap++)
		   {
		      for (int i = 0, j = gap; j < n; i++, j++)
		      {
		          if (j < i+2)
		             table[i][j] = 0.0;
		          else
		          {
		              table[i][j] = 1000000.0;
		              for (int k = i+1; k < j; k++)
		              {
		                double val = table[i][k] + table[k][j] + cost(ps,i,j,k);
		                if (table[i][j] > val){
		                     table[i][j] = val;
		                     valK[i][j]=k;
		                }
		              }
		          }
		      }		      
		      System.out.println("\n\n\n\n");
		      for(int i1=0; i1<n; ++i1){
		    	  for(int j1=0; j1<n; ++j1){
		    		  System.out.print(table[i1][j1] + " ");
		    	  }
		    	  System.out.println("");
		      }
		   }
		LineCaller(0,n-1,valK);
		DrawLines();
		return  table[0][n-1];
	}
	
	@SuppressWarnings("serial")
	public static class myWindow extends JFrame{
		public myWindow(){
			this.setSize(900,900);
			this.setBackground(Color.white);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.add(new DrawingBoard(),BorderLayout.CENTER);
			this.setVisible(true);
		}
	}
	
	@SuppressWarnings("serial")
	public static class DrawingBoard extends JComponent
	{
		public void	paint(Graphics g)
		{
			Graphics2D graph2= (Graphics2D)g;
			graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for(int i=0; i<2*n-3; ++i){
				int a,b,c,d;
				a= ps.get(vertices[i][0]).x *100;
				b= ps.get(vertices[i][0]).y *100;
				c= ps.get(vertices[i][1]).x *100;
				d= ps.get(vertices[i][1]).y *100;
				System.out.println("Printing " + a + "," + b + " to " + c + "," + d + "\n");
				Shape drawLine = new Line2D.Float(a,b,c,d);
				graph2.draw(drawLine);
			}
		}
		
	}
	
	private static void DrawLines() {
		new myWindow();
	}
	
	private static void LineCaller(int i, int j, int[][] valK) {
		System.out.println("Draw line between " + i + " and " + j + "\n");
		if(i==j)
			return;
		vertices[ind][0]=i;
		vertices[ind++][1]=j;
		if(i == j-1)
			return;
		LineCaller(i,valK[i][j],valK);
		LineCaller(valK[i][j],j,valK);
	}

	private static double cost(ArrayList<Point> ps, int i, int j, int k) {
		
		Point p1 = ps.get(i), p2 = ps.get(j), p3 = ps.get(k);
	    return dist(p1, p2) + dist(p2, p3) + dist(p3, p1);
	    
	}

	private static double dist(Point p1, Point p2) {
		return  Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) +
                (p1.y - p2.y)*(p1.y - p2.y));
	}

}
