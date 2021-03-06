package clientmod.bnclient;

import java.net.DatagramSocket;
import java.util.ArrayList;

import bnprotocol.BnUdpClientProtocolInterface;
import bnprotocol.BnUdpServerProtocolInterface;

/**
 * 
 * @author levymateus
 *
 */
public class AbstractClient implements BnUdpServerProtocolInterface, BnUdpClientProtocolInterface{
	
	/*
	 * Socket do cliente
	 */
	protected static DatagramSocket socket = null;
	
	/**
	 * Retornado pelo metodo loginAttempt em caso de tempo limite exedido.
	 * Tempo limite acontece quando se esgotam as tentativas máximas de conexão com o 
	 * servidor.
	 */
	public static final String TIMED_OUT = "-1";
	
	/**
	 * Status de login do cliente
	 */
	protected boolean loginStatus = true;
	
	/**
	 * 
	 */
	protected ArrayList<String> listOfconnections = new ArrayList<>(MAX_NUMBER_CONNECTED);

	public static DatagramSocket getSocket() {
		return socket;
	}
	
}
