import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class TicTacToeTest {

	FindNextMove minMAx;


	@BeforeEach
	void init()
	{
		minMAx = new FindNextMove();

	}

	@Test
	void t1()
	{
		minMAx.userBoard = "b b b " + "b b b " + "b b b";

		minMAx.startMinMax();
		assertEquals(1, minMAx.computerMove, "Incorrect Computer's Move");

	}

	@Test
	void t2()
	{
		minMAx.userBoard = "O b b " + "b X b " + "b b b";
		minMAx.startMinMax();

		assertEquals(2, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t3()
	{
		minMAx.userBoard = "X X b " + "O b b " + "O b b";
		minMAx.startMinMax();

		assertEquals(3, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t8()
	{
		minMAx.userBoard = "X b b " + "b b b " + "b b O";
		minMAx.startMinMax();

		assertEquals(3, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t4()
	{
		minMAx.userBoard = "O O X " + "b X b " + "b b b";
		minMAx.startMinMax();

		assertEquals(4, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t9()
	{
		minMAx.userBoard = "X O X " + "b b b " + "b b O";
		minMAx.startMinMax();

		assertEquals(4, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t5()
	{
		minMAx.userBoard = "X X O " + "O b b " + "O b b";
		minMAx.startMinMax();

		assertEquals(5, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t6()
	{
		minMAx.userBoard = "O O X " + "O X b " + "b b X";
		minMAx.startMinMax();

		assertEquals(6, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t7()
	{
		minMAx.userBoard = "X O O " + "O X X " + "O X b";
		minMAx.startMinMax();

		assertEquals(9, minMAx.computerMove, "Incorrect Computer's move");
	}

	@Test
	void t10()
	{
		minMAx.userBoard = "X O X " + "X b b " + "O b O";
		minMAx.startMinMax();

		assertEquals(8, minMAx.computerMove, "Incorrect Computer's move");
	}



}
