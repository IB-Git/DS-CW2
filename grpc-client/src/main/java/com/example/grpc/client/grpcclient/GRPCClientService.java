package com.example.grpc.client.grpcclient;

import com.example.grpc.server.grpcserver.MatrixRequest;
import com.example.grpc.server.grpcserver.MatrixReply;
import com.example.grpc.server.grpcserver.MatrixServiceGrpc;

import com.example.grpc.server.grpcserver.PingRequest;
import com.example.grpc.server.grpcserver.PongResponse;
import com.example.grpc.server.grpcserver.PingPongServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File; 
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.Random;

import com.example.grpc.client.model.FileUploadResponse;

@Service
public class GRPCClientService {

        private String fileName;
        private String uploadFilePath;
        private String contentType;
        private File dest;

        @Value("${matrix.symbols}")
        private String matrixSymbols;

        public String ping() {
                ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();        
                PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);        
                PongResponse helloResponse = stub.ping(PingRequest.newBuilder()
                .setPing("")
                .build());   

                channel.shutdown();        
                return helloResponse.getPong();
        }

        // File upload endpoint  
        public FileUploadResponse fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("deadline") int deadline){
                
                fileName = file.getOriginalFilename(); // get file name 
                String filePathServer = "/home/ubuntu/grpcNew/Files"; // use to save file for server development 


                uploadFilePath = filePathServer;

                contentType = file.getContentType();
                dest = new File(uploadFilePath + '/' + fileName);

                if (!dest.getParentFile().exists())  dest.getParentFile().mkdirs(); // make directory if doesn't exist
                    
        
                try { file.transferTo(dest); }
                catch (Exception e) { return new FileUploadResponse(fileName, contentType, "File not provided." + e.getMessage()); }
                String matrixA_temp = txt2String(dest).split(matrixSymbols)[0];
                String matrixB_temp = txt2String(dest).split(matrixSymbols)[1];
                int[][] matrixA = convertToMatrix(matrixA_temp);
                int[][] matrixB = convertToMatrix(matrixB_temp);
                if(matrixA.length != matrixA[0].length || matrixB.length != matrixB[0].length){
                        String data  = "Matrix A: " + matrixA.length  + "x" + matrixA[0].length;
                               data += "  Matrix B: " + matrixB.length  + "x" + matrixB[0].length;
                        return new FileUploadResponse(fileName, contentType, "Rows and Columns should be of equal size. " + data);
                }
                if(matrixA.length % 4 !=0 || matrixB.length % 4 !=0 ){
                        String data  = "Matrix A: " + matrixA.length  + "x" + matrixA[0].length;
                               data += "  Matrix B: " + matrixB.length  + "x" + matrixB[0].length;
                        return new FileUploadResponse(fileName, contentType, "Accepted Matrices: nxn where n%4=0" + data);
                }
                grpcClient(matrixA, matrixB, deadline);
                return new FileUploadResponse(fileName, contentType, "File Uploaded");
        }

        public void grpcClient(int[][]a, int[][]b, int deadline){
                System.out.println("\n=====================================");
                System.out.println("Deadline: " + deadline + " seconds");

                String gcp1 = "";
                String gcp2 = "";
                String gcp3 = "";
                String gcp4 = "";
                String gcp5 = "";
                String gcp6 = "";
                String gcp7 = "";
                String gcp8 = "";


                ManagedChannel channel1 = ManagedChannelBuilder.forAddress(gcp1, 9090).usePlaintext().build();
                ManagedChannel channel2 = ManagedChannelBuilder.forAddress(gcp2, 9090).usePlaintext().build();
                ManagedChannel channel3 = ManagedChannelBuilder.forAddress(gcp3, 9090).usePlaintext().build();
                ManagedChannel channel4 = ManagedChannelBuilder.forAddress(gcp4, 9090).usePlaintext().build();
                ManagedChannel channel5 = ManagedChannelBuilder.forAddress(gcp5, 9090).usePlaintext().build();
                ManagedChannel channel6 = ManagedChannelBuilder.forAddress(gcp6, 9090).usePlaintext().build();
                ManagedChannel channel7 = ManagedChannelBuilder.forAddress(gcp7, 9090).usePlaintext().build();
                ManagedChannel channel8 = ManagedChannelBuilder.forAddress(gcp8, 9090).usePlaintext().build();


                MatrixServiceGrpc.MatrixServiceBlockingStub stub1 = MatrixServiceGrpc.newBlockingStub(channel1);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub2 = MatrixServiceGrpc.newBlockingStub(channel2);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub3 = MatrixServiceGrpc.newBlockingStub(channel3);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub4 = MatrixServiceGrpc.newBlockingStub(channel4);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub5 = MatrixServiceGrpc.newBlockingStub(channel5);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub6 = MatrixServiceGrpc.newBlockingStub(channel6);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub7 = MatrixServiceGrpc.newBlockingStub(channel7);
                MatrixServiceGrpc.MatrixServiceBlockingStub stub8 = MatrixServiceGrpc.newBlockingStub(channel8);

                ArrayList<MatrixServiceGrpc.MatrixServiceBlockingStub> stubss = new ArrayList<MatrixServiceGrpc.MatrixServiceBlockingStub>();
                stubss.add(stub1);
                stubss.add(stub2);
                stubss.add(stub3);
                stubss.add(stub4);
                stubss.add(stub5);
                stubss.add(stub6);
                stubss.add(stub7);
                stubss.add(stub8);

                int stubs_index = 0;

                int N = a.length;

                DecimalFormat df = new DecimalFormat("#.##"); 
                Random r = new Random();
                int low = 0;
                int high = 8;
                int random = r.nextInt(high-low) + low;
                double footprint = Double.valueOf(df.format(footPrint(stubss.get(random), a[0][0], a[N-1][N-1])));

                int number_of_calls = (int) Math.pow(N, 2);
                double execution_time = number_of_calls*footprint;
                double number_of_server_needed = execution_time/deadline;



                if (number_of_server_needed < 1.00 ) number_of_server_needed = 1.00;
                if(number_of_server_needed <2.00 && number_of_server_needed > 1.00) number_of_server_needed = 2.00;
                
                System.out.println("Number of server needed: " + number_of_server_needed);
                System.out.println("=====================================");
                System.out.println("Footprint: " + footprint + " seconds");
                System.out.println("=====================================");
                
                

                if((number_of_server_needed >= 8) ){
                        number_of_server_needed = 8;
                        if(deadline <= 50){
                                System.out.println("Footprint: " + footprint + "\nFootprint x number of calls: " + (footprint*number_of_calls));
                                System.out.println("The load exceeds the deadline, multiplication cannot be done!");
                                return;
                        }
                }

                int number_of_servers_in_use = (int) Math.round(number_of_server_needed);
                System.out.println("Number of used servers: " + number_of_servers_in_use);
                System.out.println("=====================================\n");
                int c[][] = new int[N][N];

                // Start the matrix calculation and print the result onto client 
                for (int i = 0; i < N; i++) { // row
                        for (int j = 0; j < N; j++) { // col
                            for (int k = 0; k < N; k++) {
                                
                                MatrixReply temp=stubss.get(stubs_index).multiplyBlock(MatrixRequest.newBuilder().setA(a[i][k]).setB(b[k][j]).build());
                                if(stubs_index == number_of_servers_in_use-1) stubs_index = 0;
                                else stubs_index++;
                                MatrixReply temp2=stubss.get(stubs_index).addBlock(MatrixRequest.newBuilder().setA(c[i][j]).setB(temp.getC()).build());
                                c[i][j] = temp2.getC();
                                if(stubs_index == number_of_servers_in_use-1) stubs_index = 0;
                                else stubs_index++;
                            }
                        }
                    }

                    // Print result matrix
                    for (int i = 0; i < a.length; i++) {
                        for (int j = 0; j < a[0].length; j++) {
                            System.out.print(c[i][j] + " ");
                        }
                        System.out.println("");
                    }
                // Close channels
                channel1.shutdown();
                channel2.shutdown();
                channel3.shutdown();
                channel4.shutdown();
                channel5.shutdown();
                channel6.shutdown();
                channel7.shutdown();
                channel8.shutdown();
                
        }
        // calculate footprinting and return seconds
        private static double footPrint(MatrixServiceGrpc.MatrixServiceBlockingStub stub, int a, int b){

                double startTime = System.nanoTime();
                MatrixReply temp=stub.multiplyBlock(MatrixRequest.newBuilder().setA(a).setB(b).build());
                double endTime = System.nanoTime();
                double footprint= endTime-startTime;
                return (footprint/1000000000);
        }
        public static int[][] convertToMatrix(String m){

                String[] data = m.split(";");
                String row_col[] = data[0].split(",");
                int row = Integer.parseInt(row_col[0].replaceAll("[\\n\\t ]", ""));
                int col = Integer.parseInt(row_col[1].replaceAll("[\\n\\t ]", ""));

                String[] matrixData_temp = data[1].split(" ");
               
                int[][] matrix = new int[row][col];
                int temp_matrix_index = 0; 
                 
                for(int i = 0; i < row; i++){
                        for(int j = 0; j < col; j++){
                                matrix[i][j] = Integer.parseInt(matrixData_temp[temp_matrix_index].replaceAll("[\\n\\t ]", ""));
                                temp_matrix_index++;
                        }
                }
                return matrix;
        }

        public static String txt2String(File file) {
                StringBuilder result = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String s = null;
                    while ((s = br.readLine()) != null) {
                        result.append(System.lineSeparator() + s);
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result.toString();
        }



}