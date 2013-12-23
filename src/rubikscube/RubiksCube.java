package rubikscube;

import java.util.Arrays;

public class RubiksCube {

	private final int SIDES = 6;
	/* 
	 * Red = 0
	 * Green = 1
	 * Yellow = 2
	 * Blue = 3
	 * Orange = 4
	 * White = 5
	 */

	private final int ROWS = 3; 		
	private final int COLUMNS = 3;
	public String state [][][] = new String [SIDES][ROWS][COLUMNS];
	int index = 0;
	/*
	 * main for testing purposes
	 * format for input string "G W Y O B R B"
	 * format for direction to rotate "R1O2W"
	 */


	public static void main(String[] args) {
		//RubiksCube game = new RubiksCube("G G W R R G R R G O W W O G O Y Y Y G G O Y Y Y R B G Y Y R R B R R W W B O Y B O B B O B O G O W W B W W B");
		//RubiksCube game = new RubiksCube("RRRRRRRRRGGGGGGGGGYYYYYYYYYBBBBBBBBBOOOOOOOOOWWWWWWWWW");
                RubiksCube game = new RubiksCube("R R R R R R R R R G G G G G G G G G Y Y Y Y Y Y Y Y Y B B B B B B B B B O O O O O O O O O W W W W W W W W W");
                System.out.println(game);
//		String d = "Y2";
//		game.rotate(d);
//                System.out.println(game);
//		d = "Y2";
//		game.rotate(d);
//                System.out.println(game);
                
	}//end of main	


	public RubiksCube(String initialState) {

		fillArray(initialState);
	}


	/*
	 * high level rotation of the cube
	 * clockwise rotation of a side of the cube
	 * 
	 */
	public void rotate(String direction) {
		//use to charArray()?????
		String [] tokens = new String [direction.length()];
		String rotate;
		int turns;
		// Tokenizes the rotation directions
		index = 0;
		for (int i = 0; i <tokens.length; i++) 
		{

			tokens[i] = direction.substring(i, i+1);
		}

		//Use the rotation directions to manipulate the cube, changing to the rotated state
		for (int i = 0; i <tokens.length; i = i+2)
		{
			String color = tokens[i]; 

			turns = Integer.parseInt(tokens[i+1]);
			
			rotateFace(turns, color);
		}

	}









	/*
	 * rotates the face clockwise the number of turns
	 * returns the new string representing the turned side 
	 */
	public void rotateFace(int turns, String color) {
		//System.out.println("in rotateFace() color is " + color);
		switch (color)
		{
		case ("R"):
		//Store corner2
			String temp11 = state[0][0][2];		
			String temp12 = state[3][0][2];
			String temp13 = state[5][2][2];
			
			//move corner1 to corner2
			state[0][0][2] = state[0][0][0];
			state[5][2][2] = state[1][0][0];
			state[3][0][2] = state[5][2][0];
			
			//Store corner4 temp2
			String temp21 = state[0][2][2];
			String temp22 = state[2][0][2];
			String temp23 = state[3][0][0];
			
			//move corner2(temp1) to corner4
			state[0][2][2] = temp11;
			state[2][0][2] = temp12;
			state[3][0][0] = temp13;
			
			//store corner3 to temp1
			temp11 = state[0][2][0];
			temp12= state[1][0][2];
			temp13 = state[2][0][0];
			
			//move corner4(temp2) to corner3
			state[0][2][0] = temp21;
			state[1][0][2] = temp22;
			state[2][0][0] = temp23;
			 
			//move corner3 to corner1
			state[0][0][0] = temp11;
			state[1][0][0] = temp13;
			state[5][2][0] = temp12;
			
			//copy side4 to temp1
			temp11 = state[0][1][0];
			temp12 = state[1][0][1];
			
			//move side3 to side4
			state[0][1][0] = state[0][2][1];
			state[1][0][1] = state[2][0][1];
			
			//move side2 to side3
			state[0][2][1] = state[0][1][2];
			state[2][0][1] = state[3][0][1];
			
			//move side1 to side2
			state[0][1][2] = state[0][0][1];
			state[3][0][1] = state[5][2][1];
			
			//move side4(temp1) to side1
			state[0][0][1] = temp11;
			state[5][2][1] = temp12;
			
			break;
			
		case("G"):
			//copy corner4 to temp
			String temp1 = state[1][2][0];
			String temp2 = state[4][2][0];
			String temp3 = state[5][0][0];
			
			//move corner3 to corner4
			state[1][2][0]= state[1][2][2];
			state[4][2][0]= state[2][2][0];
			state[5][0][0]= state[4][0][0];
			
			//move corner2 to corner3
			state[2][2][0]= state[0][2][0];
			state[1][2][2]= state[1][0][2];
			state[4][0][0]= state[2][0][0];
			
			//move corner1 to corner2
			state[0][2][0]= state[5][2][0];
			state[1][0][2]= state[1][0][0];
			state[2][0][0]= state[0][0][0];
			
			//move corner4(temp) to corner1
			state[0][0][0]= temp3;
			state[1][0][0]= temp1;
			state[5][2][0]= temp2;
			

			//copy side4 to temp1
			temp11 = state[1][1][0];
			temp12 = state[5][1][0];
						
			//move side3 to side4
			state[1][1][0] = state[1][2][1];
			state[5][1][0] = state[4][1][0];
			
			//move side2 to side3
			state[1][2][1] = state[1][1][2];
			state[4][1][0] = state[2][1][0];
			
			//move side1 to side2
			state[1][1][2] = state[1][0][1];
			state[2][1][0] = state[0][1][0];
			
			//move side4(temp1) to side1
			state[0][1][0] = temp12;
			state[1][0][1] = temp11;
			
			break;
			
			case("Y"):
				//copy corner4 to temp
				String temp4 = state[1][2][2];
				String temp5 = state[2][2][0];
				String temp6 = state[4][0][0];
				
				//move corner3 to corner4
				state[1][2][2]= state[4][0][2];
				state[2][2][0]= state[2][2][2];
				state[4][0][0]= state[3][2][0];
				
				//move corner2 to corner3
				state[2][2][2]= state[2][0][2];
				state[3][2][0]= state[0][2][2];
				state[4][0][2]= state[3][0][0];
				
				//move corner1 to corner2
				state[2][0][2]= state[2][0][0];
				state[0][2][2]= state[1][0][2];
				state[3][0][0]= state[0][2][0];
				
				//move corner4(temp) to corner1
				state[0][2][0]= temp4;
				state[1][0][2]= temp6;
				state[2][0][0]= temp5;
				

				//copy side4 to temp1
				temp11 = state[1][1][2];
				temp12 = state[2][1][0];
								
				//move side3 to side4
				state[1][1][2] = state[4][0][1];
				state[2][1][0] = state[2][2][1];
				
				//move side2 to side3
				state[2][2][1] = state[2][1][2];
				state[4][0][1] = state[3][1][0];
				
				//move side1 to side2
				state[2][1][2] = state[2][0][1];
				state[3][1][0] = state[0][2][1];
				
				//move side4(temp1) to side1
				state[0][2][1] = temp11;
				state[2][0][1] = temp12;
				
				break;				
				
				case("B"):
					//copy corner4 to temp
					String temp7 = state[2][2][2];
					String temp8 = state[3][2][0];
					String temp9 = state[4][0][2];
					
					//move corner3 to corner4
					state[2][2][2]= state[4][2][2];
					state[3][2][0]= state[3][2][2];
					state[4][0][2]= state[5][0][2];
					
					//move corner2 to corner3
					state[3][2][2]= state[3][0][2];
					state[4][2][2]= state[5][2][2];
					state[5][0][2]= state[0][0][2];
					
					//move corner1 to corner2
					state[0][0][2]= state[2][0][2];
					state[3][0][2]= state[3][0][0];
					state[5][2][2]= state[0][2][2];
					
							//move corner4(temp) to corner1
					state[0][2][2]= temp7;
					state[2][0][2]= temp9;
					state[3][0][0]= temp8;
					

					//copy side4 to temp1
					temp11 = state[2][1][2];
					temp12 = state[3][1][0];
					
					//move side3 to side4
					state[2][1][2] = state[4][1][2];
					state[3][1][0] = state[3][2][1];
					
					//move side2 to side3
					state[4][1][2] = state[5][1][2];
					state[3][2][1] = state[3][1][2];
					
					//move side1 to side2
					state[5][1][2] = state[0][1][2];
					state[3][1][2] = state[3][0][1];
					
					//move side4(temp1) to side1
					state[0][1][2] = temp11;
					state[3][0][1] = temp12;
					
					break;
						
					case("O"):
						//copy corner4 to temp
						String temp14 = state[1][2][0];
						String temp15 = state[4][2][0];
						String temp16 = state[5][0][0];
						
						//move corner3 to corner4
						state[1][2][0]= state[5][0][2];
						state[4][2][0]= state[4][2][2];
						state[5][0][0]= state[3][2][2];
						
						//move corner2 to corner3
						state[3][2][2]= state[2][2][2];
						state[4][2][2]= state[4][0][2];
						state[5][0][2]= state[3][2][0];
						
						//move corner1 to corner2
						state[2][2][2]= state[1][2][2];
						state[4][0][2]= state[4][0][0];
						state[3][2][0]= state[2][2][0];
						
						//move corner4(temp) to corner1
						state[1][2][2]= temp16;
						state[2][2][0]= temp14;
						state[4][0][0]= temp15;
						

						//copy side4 to temp1
						String temp17 = state[1][2][1];
						String temp18 = state[4][1][0];						
												
						//move side3 to side4
						state[1][2][1] = state[5][0][1];
						state[4][1][0] = state[4][2][1];
						
						//move side2 to side3
						state[5][0][1] = state[3][2][1];
						state[4][2][1] = state[4][1][2];
						
						//move side1 to side2
						state[3][2][1] = state[2][2][1];
						state[4][1][2] = state[4][0][1];
						
						//move side4(temp1) to side1
						state[2][2][1] = temp17;
						state[4][0][1] = temp18;
						
						break;
				
						case("W"):
							//copy corner4 to temp
							String temp19 = state[0][0][0];
							String temp20 = state[1][0][0];
							String temp24 = state[5][2][0];
							
							//move corner3 to corner4
							state[0][0][0]= state[3][0][2];
							state[1][0][0]= state[0][0][2];
							state[5][2][0]= state[5][2][2];
							
							//move corner2 to corner3
							state[0][0][2]= state[3][2][2];
							state[3][0][2]= state[4][2][2];
							state[5][2][2]= state[5][0][2];
							
							//move corner1 to corner2
							state[3][2][2]= state[4][2][0];
							state[4][2][2]= state[1][2][0];
							state[5][0][2]= state[5][0][0];
							
							//move corner4(temp) to corner1
							state[4][2][0]= temp20;
							state[1][2][0]= temp19;
							state[5][0][0]= temp24;
							
							//copy side4 to temp1
							String temp25 = state[1][1][0];
							String temp26 = state[5][1][0];
						
							//move side3 to side4
							state[1][1][0] = state[0][0][1];
							state[5][1][0] = state[5][2][1];
							
							//move side2 to side3
							state[0][0][1] = state[3][1][2];
							state[5][2][1] = state[5][1][2];
							
							//move side1 to side2
							state[3][1][2] = state[4][2][1];
							state[5][1][2] = state[5][0][1];
							
							//move side4(temp1) to side1
							state[4][2][1] = temp25;
							state[5][0][1] = temp26;
							
							break;
			
		}


		turns--;
		if(turns > 0)
		{
			rotateFace(turns, color);
		}
		
	}


	/*
	 * returns the filled side of the cube 
	 */
	public String[][] fillSide(String ordered ){

		String[][] manipulated = new String[3][3];
		int index = 0;
		for(int i = 0; i < manipulated.length; i++) 
		{
			for (int j = 0; j < manipulated[i].length; j++)
			{

				if (index == ordered.length())
					manipulated[i][j] = ordered.substring(ordered.length());

				else
					manipulated[i][j] = (ordered.substring(index, index + 1));

				index++;
			}
		}

		return manipulated;
	}

	public int[] getColors(String s) {
		int color = 0;
		int [] colors = new int[5];
		switch (s) {
		case "R": 
			colors[0] = 0;	
			colors[1] = 1;
			colors[2] = 2;
			colors[3] = 3;
			colors[4] = 5;
			break;

		case "G": color = 1;
		colors[1] = 1;
		colors[2] = 0;
		colors[3] = 2;
		colors[4] = 4;
		colors[5] = 5;
		break;

		case "Y": color = 2;
		break;
		case "B": color = 3;
		break;
		case "O": color = 4;
		break;
		case "W": color = 5;
		break;
		default:
			break;
		}
		return colors;
	}
	
	

	
	/* Do I still need this?????
	 * returns the 2D array of the color requested
	 */
	public String[][] getSide(String color){

		String[][] side = new String[3][3];		

		switch (color)
		{
		case "R": side = state[0].clone();
		break;

		case "G": side = state[1].clone();
		break;

		case "Y": side = state[2].clone();
		break;

		case "B": side = state[3].clone();
		break;

		case "O": side = state[4].clone();
		break;

		case "W": side = state[5].clone();
		break;
		}
		return side;
	}




	/*
	 * returns the 3 faces of the corner cubies	
	 */

	public String getCornerCubies() {
		String t = null;

		//Corner Cube1
		String s =   state[2][0][0];
		s = s.concat(state[1][0][2]);
		s = s.concat(state[0][2][0]);

		//Corner Cube2
		s = s.concat(state[2][0][2]);
		s = s.concat(state[3][0][0]);
		s = s.concat(state[0][2][2]);

		//Corner Cube3
		s = s.concat(state[2][2][0]);
		s = s.concat(state[1][2][2]);
		s = s.concat(state[4][0][0]);

		//Corner Cube4
		s = s.concat(state[2][2][2]);
		s = s.concat(state[3][2][0]);
		s = s.concat(state[4][0][2]);

		//Corner Cube5
		s = s.concat(state[5][0][0]);
		s = s.concat(state[1][2][0]);
		s = s.concat(state[4][2][0]);

		//Corner Cube6
		s = s.concat(state[5][0][2]);
		s = s.concat(state[3][2][2]);
		s = s.concat(state[4][2][2]);

		//Corner Cube7
		s = s.concat(state[5][2][0]);
		s = s.concat(state[1][0][0]);
		s = s.concat(state[0][0][0]);

		t = s;
		//}
		return t;
	}


	public String getFirstSixSideCubies() {
		String t = null;

		//Sides Cube1
		String s =   state[5][2][1];			
		s = s.concat(state[0][0][1]);


		//Sides Cube2
		s = s.concat(state[1][0][1]);
		s = s.concat(state[0][1][0]);

		//Sides Cube3
		s = s.concat(state[3][0][1]);
		s = s.concat(state[0][1][2]);

		//Sides Cube4
		s = s.concat(state[2][0][1]);
		s = s.concat(state[0][2][1]);

		//Sides Cube5
		s = s.concat(state[2][1][0]);
		s = s.concat(state[1][1][2]);

		//Sides Cube6
		s = s.concat(state[2][1][2]);
		s = s.concat(state[3][1][0]);

		t = s;

		return t;
	}

	public String getOtherSixSideCubies() {
		String t = null;

		//Sides Cube1
		String s =   state[5][1][0];			
		s = s.concat(state[1][1][0]);

		//Sides Cube2
		s = s.concat(state[1][2][1]);
		s = s.concat(state[4][1][0]);

		//Sides Cube3
		s = s.concat(state[2][2][1]);			
		s = s.concat(state[4][0][1]);

		//Sides Cube4
		s = s.concat(state[3][2][1]);			
		s = s.concat(state[4][1][2]);

		//Sides Cube5
		s = s.concat(state[5][0][1]);
		s = s.concat(state[4][2][1]);

		//Sides Cube6
		s = s.concat(state[5][1][2]);
		s = s.concat(state[3][1][2]);

		t = s;

		return t;

	}


	/*
	 * from the input string finds the center color of each side to correctly orient the sides 
	 */
	
	public String[] getCenter(String[] tokens) {
		String[] side = new String[54];
		String[][][] ordered = new String [6][3][3];
		for (int i = 4; i <=49; i = i +9)
		{
			String s = tokens[i];

			if (s.equalsIgnoreCase("R"))
			{
				System.arraycopy(tokens, i - 4, side, 0, 9);


			}
			else
				if (s.equalsIgnoreCase("G"))
				{
					System.arraycopy(tokens, i - 4, side, 9, 9);

				}
				else
					if (s.equals("Y")) 
					{
						System.arraycopy(tokens, i -4, side, 18, 9);

					}
					else		
						if (s.equals("B"))
						{
							System.arraycopy(tokens, i -4, side, 27, 9);

						}
						else
							if (s.equals("O"))
							{
								System.arraycopy(tokens, i -4, side, 36, 9);

							}
							else
								if (s.equals("W"))
								{
									System.arraycopy(tokens, i -4, side, 45, 9);

								}
		}

		System.arraycopy(side, 0, tokens, 0, 54);
		return tokens;
	}

	/*
	 * creates a string out of a 2D array
	 */
	public String arrayToString(String[][] side) {
		String s = "";
		for (int i = 0; i < side.length; i++)
		{
			for (int j = 0; j < side[i].length; j++)
			{
				s = s.concat(side[i][j]);

			}

		}
		return s;
	}

	/*
	 * returns the representation of the cube in a 3D array 
	 */
	public String[][][] fillArray(String initialState) {
		String t = initialState.toUpperCase();
                String[] tokens = t.split("\\s");
		tokens = getCenter(tokens);
		int index = 0;
		for(int i = 0; i < state.length; i++) 
		{
			for (int j=0; j < state[i].length; j++)
			{
				for (int k=0; k < state[i][j].length; k++)
				{
					state[i][j][k] = (tokens[index++]);
				}

			}
		}
		return state;
	}

	protected void setState(String[][][] state) {
		this.state = state;
	}

/*
 * returns the 3D array representing the state of the game
 */
	protected String[][][] getState() {
		return state;
	}

/*
	String[] formatString(String input) {
		String t = input.toUpperCase();

		String[] tokens = t.split("\\s");
		return tokens;
	}
*/

	@Override
	/*
	 * Returns a the cube's state represented as a string of the colors
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		String s = "";
		for(int i = 0; i < state.length; i++) 
		{
			for (int j=0; j < state[i].length; j++)
			{
				for (int k=0; k < state[i][j].length; k++) 
				{
					String t = (state[i][j][k]);
					s = s.concat(t);

				}
			}
		}
		return s;
	}
}