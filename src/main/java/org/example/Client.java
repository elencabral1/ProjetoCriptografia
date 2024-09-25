package org.example;

import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Scanner;

public class Client {
    Socket socket;

    public void comunicarComServidor() throws Exception {
        String textoRequisicao = "Conexao estabelecida";
        String textoRecebido = "";
        String textoDecifrado = "";
        String textoCifrado = "";

        //socket = new Socket("192.168.1.10", 9600);
        socket = new Socket("localhost", 9600);

        // Gerar chave pública e privada
        System.out.println("Gerando chaves RSA...");
        KeyPair chaves = CriptografiaClienteServidor.gerarChavesPublicoPrivada();

        Scanner input = new Scanner(System.in);
        // receber a chave pública do servidor
        System.out.println("Recebendo chave pública do servidor...");
        PublicKey chavePublica = Conexao.receberChave(socket);

        // enviar chave pública para o servidor
        System.out.println("Enviando chave pública...");
        Conexao.enviarChave(socket, chaves.getPublic());

        System.out.print("\nDigite a sua mensagem: ");
        textoRequisicao = input.nextLine();

        textoCifrado = CriptografiaClienteServidor.cifrar(textoRequisicao, chavePublica);

        // Enviar mensagem para o servidor
        System.out.println("Mensagem criptografada enviada: " + textoCifrado);
        Conexao.enviar(socket, textoCifrado);

        // Receber mensagem do servidor
        textoRecebido = Conexao.receber(socket);

        // Decifrar texto do servidor
        textoDecifrado = CriptografiaClienteServidor.decifrar(textoRecebido, chaves.getPrivate());

        System.out.println("\nMensagem criptografada recebida: " + textoRecebido);
        System.out.println("Servidor enviou: " + textoDecifrado);
    }


    public static void main(String[] args) {
        try {
            Client cliente = new Client();
            cliente.comunicarComServidor();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

