package test.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class AbstractControllerTest<T, J> {
	@Captor
	protected ArgumentCaptor<T> captor;
	protected J controller;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private ByteArrayInputStream inContent;

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		MockitoAnnotations.openMocks(this);
	}

	protected void provideInput(String data) {
		inContent = new ByteArrayInputStream(data.getBytes());
		System.setIn(inContent);
	}

	protected String getOutput() {
		return outContent.toString();
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setIn(System.in);
		outContent.reset();
	}
}