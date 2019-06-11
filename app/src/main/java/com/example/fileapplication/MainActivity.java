package com.example.fileapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    /**
     * 读写 放入指定位置
     */
    private String inPath = Environment.getExternalStorageDirectory()+"/a.txt";
    private String outPath = Environment.getExternalStorageDirectory()+"/text.txt";

    /**
     * 位于项目的 data 目录下
     */
    private String dataPath ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        // 运行时权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                initData();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        11);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
            initData();
        }
    }

    /**
     *  运行时权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 11: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initData();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void initData() {
//        dataPath = this.getFilesDir() + File.separator+"a.txt";
//        flowPath(inPath);

        // 暂时一个问题，要把项目卸载，从新安装，才能看到效果

        // 写入测试文件
//        boolean isSave=saveFile(inPath);
//        if (isSave){
//            Toast.makeText(this,"写入完成",Toast.LENGTH_LONG).show();
//        }

        // 读写文件
//        characterStream(inPath);
//        byteStream(inPath);
//        readFileByLine(inPath);
//        transform(inPath);

        // 从指定路径的文件读取内容
//        String con=getString(inPath);
        // 从assets或raw中读取内容
//        String con=getString("a.txt");
//        Log.e("TAG", con);

        // 随机访问文件
//        readRandomAccessFile(inPath);

        // 删除测试文件
//        deleteFiles(new File(inPath));
//        deleteFiles(new File(outPath));
    }

    /**
     *  流的一般使用流程
     */
    private void flowPath(String filePath) {
        // 1.创建文件对象
        File file = new File(filePath);
        FileReader fileReader ;
        BufferedReader bufferedReader ;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // 2.用流装载文件
            fileReader = new FileReader(file);

//            如果遇到字节流要转换成字符流，则在缓冲区前加一步
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            或者需要编码转换的，则在缓冲区前加一步
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");

            // 3.如果用缓冲区，则用缓冲区装载流，用缓冲区是为了提高读写性能
            bufferedReader = new BufferedReader(fileReader);
            // 4.开始读写操作
            String str ;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  字节流
     */
    private void byteStream(String filePath) {
        File file = new File(filePath);

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(outPath);
            int temp ;
            while ((temp = inputStream.read())!= -1){
                outputStream.write(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream !=null && outputStream!=null){
                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  字符流
     */
    private void characterStream(String filePath) {
        File file = new File(filePath);

        FileReader fileReader = null;
        FileWriter fileWriter = null ;

        try {
            fileReader = new FileReader(file);
            fileWriter = new FileWriter(outPath);
            int temp ;
            while ((temp = fileReader.read())!=-1){
                fileWriter.write((char)temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileReader!=null && fileWriter!=null){
                try {
                    fileReader.close();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按行读写
     */
    private void readFileByLine(String inPath) {
        File file = new File(inPath);

        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try {

            bufReader = new BufferedReader(new FileReader(file));
            bufWriter = new BufferedWriter(new FileWriter(outPath));

            String temp = null;
            while ((temp = bufReader.readLine()) != null) {
                bufWriter.write(temp+"\n");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufReader != null && bufWriter != null) {
                try {
                    bufReader.close();
                    bufWriter.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    /**
     *  从 assets 中 或 raw 读取 txt 文件
     *
     *  一、共同点：
     *  目录下的资源会被原封不动的拷贝到APK中，而不会像其它资源文件那样被编译成二进制的形式。
     *  二、区别
     *  1、最直观的就是获取它们的 InputStream 的API不一样。
     *  获取assets资源：InputStream assets = getAssets().open("xxxx");
     *  获取raw资源：InputStream raw = getResources().openRawResource(R.raw.xxxx)
     *  2、assets 下可以创建目录结构，而 res/raw 不可以。
     *  3、assets 能够动态的列出assets中的所有资源 getAssets().list(String path); ，而 res/raw 不可以。
     *  4、raw 在 Android XML文件中也可以@raw/的形式引用到它，而 assets 不可以。
     *
     */
    public String getString(String inPath) {
        InputStream inputStream =null;
        BufferedReader bufferedReader = null;
        try {
            // 从指定文件读取内容
            inputStream=new FileInputStream(new File(inPath));
            // 从 main/assets 目录下读取 txt 文件
//            inputStream = getAssets().open(inPath);
            // 从 res/raw 目录下读取 txt 文件
//            inputStream = getResources().openRawResource(R.raw.a);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null && bufferedReader != null) {
                try {
                    inputStream.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     *  需要将字节流转换为字符流时
     */
    private String transform(String inPath) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL("https://www.baidu.com/");
            URLConnection urlconnnection = url.openConnection();
            inputStream = urlconnnection.getInputStream();
            // 字节流转字符流，并且设置编码格式
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GB2312");
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer webContent = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                webContent.append(str);
            }
            int ipStart = webContent.indexOf("[") + 1;
            int ipEnd = webContent.indexOf("]");
            return webContent.substring(ipStart, ipEnd);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null && bufferedReader != null) {
                try {
                    inputStream.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     *  写入文件
     */
    public  boolean saveFile(String inPath){
        File file = new File(inPath);
        try {
            //文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //写数据
            fos.write(("zhouzhou 222").getBytes());
            fos.flush();
            //关闭文件流
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  flie：要删除的文件夹的所在位置
     */
    public static void deleteFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFiles(f);
            }
            // 如要保留文件夹，只删除文件，请注释这行
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }}

    /**
     *  随机访问文件
     */
    private void readRandomAccessFile(String inPath)  {
        String fileName = inPath;
        File fileObject = new File(inPath);

        if (!fileObject.exists()) {
            try {
                initialWrite(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            readFile(fileName);
            readFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String fileName) throws IOException {
        // "rw"	该文件以读写模式打开。 如果文件不存在，则创建该文件。
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        int counter = raf.readInt();
        String msg = raf.readUTF();

        Log.e("TAG","counter："+counter);
        Log.e("TAG","msg："+msg);
        incrementReadCounter(raf);
        raf.close();
    }

    public static void incrementReadCounter(RandomAccessFile raf)
            throws IOException {
        // 获取文件指针的值
        long currentPosition = raf.getFilePointer();
        raf.seek(0);
        int counter = raf.readInt();
        counter++;
        // 将文件指针设置在文件中的特定位置
        raf.seek(0);
        raf.writeInt(counter);
        raf.seek(currentPosition);
    }

    public static void initialWrite(String fileName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        raf.writeInt(0);
        raf.writeUTF("Hello world!");
        raf.close();
    }

    private void initView() {

    }
}
