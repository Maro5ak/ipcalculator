package com.ptasek.vpaapp2;

public class IpCalc {


    private int[] mask = new int[4];
    private int[] ip = new int[4];
    private int[] network = new int[4];
    private int[] broadcast = new int[4];

    private int inputMask;
    private int bitMask;
    private String inputIp;

    public String setMaskBits(int inputMask){
        for(int i = 0; i < 4; i++) mask[i] = 0;
        this.inputMask = inputMask;
        int fullBytes = inputMask / 8;
        int lastByte = inputMask % 8;
        for(int i = 0; i < fullBytes; i++) mask[i] = 255;
        if(fullBytes < 4) mask[fullBytes] = (255 >> (8 - lastByte) << (8 - lastByte));
        return mask[0] + "." + mask[1] + "." + mask[2] + "." + mask[3];
    }

    public String setNetwork(){
        for(int i = 0; i < 4; i++) network[i] = ip[i] & mask[i];
        return network[0] + "." + network[1] + "." + network[2] + "." + network[3];
    }

    public String setFirstAddress(){
        network[3] += 1;
        return network[0] + "." + network[1] + "." + network[2] + "." + network[3];
    }

    public String setLastAddress(){
        broadcast[3] -= 1;
        return broadcast[0] + "." + broadcast[1] + "." + broadcast[2] + "." +broadcast[3];
    }

    public String setBroadcast(){
        int[] wildcard = new int[4];
        for(int i = 0; i < 4; i++) {
            wildcard[i] = 255 - mask[i];
            broadcast[i] = wildcard[i] | network[i];
        }

        return broadcast[0] + "." + broadcast[1] + "." + broadcast[2] + "." + broadcast[3];
    }

    public String setAddressClass(){
        if(network[0] < 128) return "A";
        else if(network[0] < 192) return "B";
        else if(network[0] < 224) return "C";
        else return "D";
    }

    public String setSum(){
        return String.valueOf((int)Math.pow(2, 32 - inputMask));
    }

    public String setVisibility(){
        if(network[0] == 10) return "Private";
        else if(network[0] == 169 && network[1] == 254) return "APIPA";
        else if(network[0] == 100 && network[1] == 64) return "Private for providers";
        else if(inputIp.equals("127.0.0.1")) return "Loopback";
        else return "Public";
    }


    public boolean checkIp(String inputIp){
        this.inputIp = inputIp;
        if(!inputIp.contains(".")) return false;
        String[] ipStr = inputIp.split("\\.");
        if(ipStr.length < 4) return false;
        for(String s : ipStr){
            int block = Integer.parseInt(s);
            if(block < 0 || block > 255) return false;
        }
        for(int i = 0; i < 4; i++) ip[i] = Integer.parseInt(ipStr[i]);
        return true;
    }

}
