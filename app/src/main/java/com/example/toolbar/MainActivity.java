package com.example.toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageView;
    private TextView textView;
    private Uri imagePhotoCameraUri;
    private static final int START_CAMERA_APP = 1;
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 2;
    private static final int REQUEST_GALLERY_CODE = 3;
    private String filePath = "";
    private  int cornerTopX, cornerTopY, cornerBottomX, cornerBottomY, countTop, countBottom,
            legTopX, legTopY, countLegTop, legTop2X, legTop2Y, countTop2;
    private  float size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        //setTitle("");

       // toolbar.setSubtitle("Подзаголовок");
        //toolbar.setLogo(R.mipmap.ic_launcher);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_next:
                // Запускаем вторую активность
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
    public void onClickCamera(View view){
        Intent cameraAppIntent = new Intent();
        cameraAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String authorities = getApplicationContext().getPackageName() + ".fileprovider";
        imagePhotoCameraUri = FileProvider.getUriForFile(this, authorities, photoFile);
        cameraAppIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePhotoCameraUri);
        startActivityForResult(cameraAppIntent, START_CAMERA_APP);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_CAMERA_APP && resultCode == RESULT_OK) {
            imageView.setImageURI(imagePhotoCameraUri);
            startActivity(new Intent(MainActivity.this, MainActivity.class)); //reload MainActivity
            finish();

        }
    }

    private File createImageFile() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String imageFileName = "IMAGE_" + simpleDateFormat.format(new Date()) + ".jpg";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDirectory, imageFileName);
        //filePath = imageFile.getAbsolutePath();
        filePath =imageFile.getName();
        return imageFile;
    }


    public void onClickSize(View view) {
        BitmapDrawable mydrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = mydrawable.getBitmap();
        int width = bitmap.getWidth() / (bitmap.getHeight() / 200);
        int height = bitmap.getHeight() / (bitmap.getHeight() / 200);
        Bitmap bitmapSmale = Bitmap.createScaledBitmap(bitmap, width, height,false );
        bitmapSmale = doWhiteBlackBitmap(bitmapSmale);
        imageView.setImageBitmap(bitmapSmale);
        doInvert(bitmapSmale);
        size = (float)((cornerBottomY - legTopY ) * 297) / (float)(cornerBottomY - cornerTopY);
        textView.setText("("+ cornerTopX + ", " + cornerTopY + ")  " + "\n" +"("+ cornerBottomX +
                ", "+ cornerBottomY + ") " +"\n" + "("
                + legTopX + ", " + legTopY +")" + "\n" + size + "\n" + filePath);
    }
    public static Bitmap doWhiteBlackBitmap(Bitmap src) {


        // Создадим новый bitmap с теми же настройками, как у источника
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // информация о цветах
        int A, R, G, B;
        int pixel;

        // Получаем размеры изображения
        int width = src.getWidth();
        int height = src.getHeight();

        // проходим через каждый пиксель
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // работаем с отдельным пикселем
                pixel = src.getPixel(x, y);
                // получаем значение каждого цветового канала
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // применяем конвертацию
                if (R > 135 && G > 135 && B > 135) {
                    R = B = G = 255;
                }
                else{
                    R = G = B = 0;
                }
                // применяем новые значения к той же точке
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // Получаем новое изображение
        return bmOut;
    }
    public void doInvert(Bitmap src) {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int R, G, B, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18,
                R19, R20, R21, R22, R23, R24, R25, R26, R27, R28, R29, R30, R31, R32, R33, R34, R35,
                R36, R37, R38, R39, R40, R41, R42, R43, R44, R45, R46, R47, R48, R49, R50, R51, R52,
                R53, R54, R55, R56, R57, R58, R59, R60, R61, R62, R63, R64, B1, B2, B3, B4, B5, B6,
                B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23,
                B24, B25, B26, B27, B28, B29, B30, B31, B32, B33, B34, B35, B36, B37, B38, B39, B40,
                B41, B42, B43, B44, B45, B46, B47, B48, B49, B50, B51, B52, B53, B54, B55, B56, B57,
                B58, B59, B60, B61, B62, B63, B64, G1, G2, G3, G4, G5, G6, G7, G8, G9, G10, G11, G12,
                G13, G14, G15, G16, G17, G18, G19, G20, G21, G22, G23, G24, G25, G26, G27, G28, G29,
                G30, G31, G32, G33, G34, G35, G36, G37, G38, G39, G40, G41, G42, G43, G44, G45, G46,
                G47, G48, G49, G50, G51, G52, G53, G54, G55, G56, G57, G58, G59, G60, G61, G62, G63,
                G64;
        int pixelColor, pixelColor1, pixelColor2, pixelColor3, pixelColor4, pixelColor5, pixelColor6,
                pixelColor7, pixelColor8, pixelColor9, pixelColor10, pixelColor11, pixelColor12,
                pixelColor13, pixelColor14, pixelColor15, pixelColor16, pixelColor17, pixelColor18,
                pixelColor19, pixelColor20, pixelColor21, pixelColor22, pixelColor23, pixelColor24,
                pixelColor25, pixelColor26, pixelColor27, pixelColor28, pixelColor29, pixelColor30,
                pixelColor31, pixelColor32, pixelColor33, pixelColor34, pixelColor35, pixelColor36,
                pixelColor37, pixelColor38, pixelColor39, pixelColor40, pixelColor41, pixelColor42,
                pixelColor43, pixelColor44, pixelColor45, pixelColor46, pixelColor47, pixelColor48,
                pixelColor49, pixelColor50, pixelColor51, pixelColor52, pixelColor53, pixelColor54,
                pixelColor55, pixelColor56, pixelColor57, pixelColor58, pixelColor59, pixelColor60,
                pixelColor61, pixelColor62, pixelColor63, pixelColor64;
        int height = src.getHeight();
        int width = src.getWidth();
        for (int x = 10; x < width - 10; x++) {
            for (int y = 10; y < height - 10; y++) {
                pixelColor = src.getPixel(x, y);
                pixelColor1 = src.getPixel(x + 1, y);
                pixelColor2 = src.getPixel(x + 2, y);
                pixelColor3 = src.getPixel(x + 3, y);
                pixelColor4 = src.getPixel(x + 4, y);
                pixelColor5 = src.getPixel(x, y + 1);
                pixelColor6 = src.getPixel(x + 1, y + 1);
                pixelColor7 = src.getPixel(x + 2, y + 1);
                pixelColor8 = src.getPixel(x + 3, y + 1);
                pixelColor9 = src.getPixel(x + 4, y + 1);
                pixelColor10 = src.getPixel(x, y + 2);
                pixelColor11 = src.getPixel(x + 1, y + 2);
                pixelColor12 = src.getPixel(x + 2, y + 2);
                pixelColor13 = src.getPixel(x + 3, y + 2);
                pixelColor14 = src.getPixel(x + 4, y + 2);
                pixelColor15 = src.getPixel(x, y + 3);
                pixelColor16 = src.getPixel(x + 1, y + 3);
                pixelColor17 = src.getPixel(x + 2, y + 3);
                pixelColor18 = src.getPixel(x + 3, y + 3);
                pixelColor19 = src.getPixel(x + 4, y + 3);
                pixelColor20 = src.getPixel(x, y + 4);
                pixelColor21 = src.getPixel(x + 1, y + 4);
                pixelColor22 = src.getPixel(x + 2, y + 4);
                pixelColor23 = src.getPixel(x + 3, y + 4);
                pixelColor24 = src.getPixel(x + 4, y + 4);
                pixelColor25 = src.getPixel(x, y - 2);
                pixelColor26 = src.getPixel(x + 1, y - 2);
                pixelColor27 = src.getPixel(x + 2, y - 2);
                pixelColor28 = src.getPixel(x + 3, y - 2);
                pixelColor29 = src.getPixel(x + 4, y - 2);
                pixelColor30 = src.getPixel(x, y - 2);
                pixelColor31 = src.getPixel(x + 1, y - 2);
                pixelColor32 = src.getPixel(x + 2, y - 2);
                pixelColor33 = src.getPixel(x + 3, y - 2);
                pixelColor34 = src.getPixel(x + 4, y - 2);
                pixelColor35 = src.getPixel(x, y - 3);
                pixelColor36 = src.getPixel(x + 1, y - 3);
                pixelColor37 = src.getPixel(x + 2, y - 3);
                pixelColor38 = src.getPixel(x + 3, y - 3);
                pixelColor39 = src.getPixel(x + 4, y - 3);
                pixelColor40 = src.getPixel(x, y - 4);
                pixelColor41 = src.getPixel(x + 1, y - 4);
                pixelColor42 = src.getPixel(x + 2, y - 4);
                pixelColor43 = src.getPixel(x + 3, y - 4);
                pixelColor44 = src.getPixel(x + 4, y - 4);
                pixelColor45 = src.getPixel(x - 3, y + 2);
                pixelColor46 = src.getPixel(x - 2, y + 2);
                pixelColor47 = src.getPixel(x - 1, y + 2);
                pixelColor48 = src.getPixel(x, y + 2);
                pixelColor49 = src.getPixel(x + 1, y + 2);
                pixelColor50 = src.getPixel(x + 2, y + 2);
                pixelColor51 = src.getPixel(x + 3, y + 2);
                pixelColor52 = src.getPixel(x + 4, y + 2);
                pixelColor53 = src.getPixel(x + 5, y + 2);
                pixelColor54 = src.getPixel(x + 6, y + 2);
                pixelColor55 = src.getPixel(x + 7, y + 2);
                pixelColor56 = src.getPixel(x - 4, y + 2);
                pixelColor57 = src.getPixel(x - 4, y + 1);
                pixelColor58 = src.getPixel(x - 4, y);
                pixelColor59 = src.getPixel(x - 4, y - 1);
                pixelColor60 = src.getPixel(x - 4, y - 2);
                pixelColor61 = src.getPixel(x - 4, y - 3);
                pixelColor62 = src.getPixel(x - 4, y - 4);
                pixelColor63 = src.getPixel(x - 4, y - 5);
                pixelColor64 = src.getPixel(x - 4, y - 6);


                //A = Color.alpha(pixelColor);

                R = Color.red(pixelColor);
                R1 = Color.red(pixelColor1);
                R2 = Color.red(pixelColor2);
                R3 = Color.red(pixelColor3);
                R4 = Color.red(pixelColor4);
                R5 = Color.red(pixelColor5);
                R6 = Color.red(pixelColor6);
                R7 = Color.red(pixelColor7);
                R8 = Color.red(pixelColor8);
                R9 = Color.red(pixelColor9);
                R10 = Color.red(pixelColor10);
                R11 = Color.red(pixelColor11);
                R12 = Color.red(pixelColor12);
                R13 = Color.red(pixelColor13);
                R14 = Color.red(pixelColor14);
                R15 = Color.red(pixelColor15);
                R16 = Color.red(pixelColor16);
                R17 = Color.red(pixelColor17);
                R18 = Color.red(pixelColor18);
                R19 = Color.red(pixelColor19);
                R20 = Color.red(pixelColor20);
                R21 = Color.red(pixelColor21);
                R22 = Color.red(pixelColor22);
                R23 = Color.red(pixelColor23);
                R24 = Color.red(pixelColor24);
                R25 = Color.red(pixelColor25);
                R26 = Color.red(pixelColor26);
                R27 = Color.red(pixelColor27);
                R28 = Color.red(pixelColor28);
                R29 = Color.red(pixelColor29);
                R30 = Color.red(pixelColor30);
                R31 = Color.red(pixelColor31);
                R32 = Color.red(pixelColor32);
                R33 = Color.red(pixelColor33);
                R34 = Color.red(pixelColor34);
                R35 = Color.red(pixelColor35);
                R36 = Color.red(pixelColor36);
                R37 = Color.red(pixelColor37);
                R38 = Color.red(pixelColor38);
                R39 = Color.red(pixelColor39);
                R40 = Color.red(pixelColor40);
                R41 = Color.red(pixelColor41);
                R42 = Color.red(pixelColor42);
                R43 = Color.red(pixelColor43);
                R44 = Color.red(pixelColor44);
                R45 = Color.red(pixelColor45);
                R46 = Color.red(pixelColor46);
                R47 = Color.red(pixelColor47);
                R48 = Color.red(pixelColor48);
                R49 = Color.red(pixelColor49);
                R50 = Color.red(pixelColor50);
                R51 = Color.red(pixelColor51);
                R52 = Color.red(pixelColor52);
                R53 = Color.red(pixelColor53);
                R54 = Color.red(pixelColor54);
                R55 = Color.red(pixelColor55);
                R56 = Color.red(pixelColor56);
                R57 = Color.red(pixelColor57);
                R58 = Color.red(pixelColor58);
                R59 = Color.red(pixelColor59);
                R60 = Color.red(pixelColor50);
                R61 = Color.red(pixelColor51);
                R62 = Color.red(pixelColor52);
                R63 = Color.red(pixelColor53);
                R64 = Color.red(pixelColor54);

                G = Color.green(pixelColor);
                G1 = Color.green(pixelColor1);
                G2 = Color.green(pixelColor2);
                G3 = Color.green(pixelColor3);
                G4 = Color.green(pixelColor4);
                G5 = Color.green(pixelColor5);
                G6 = Color.green(pixelColor6);
                G7 = Color.green(pixelColor7);
                G8 = Color.green(pixelColor8);
                G9 = Color.green(pixelColor9);
                G10 = Color.green(pixelColor10);
                G11 = Color.green(pixelColor11);
                G12 = Color.green(pixelColor12);
                G13 = Color.green(pixelColor13);
                G14 = Color.green(pixelColor14);
                G15 = Color.green(pixelColor15);
                G16 = Color.green(pixelColor16);
                G17 = Color.green(pixelColor17);
                G18 = Color.green(pixelColor18);
                G19 = Color.green(pixelColor19);
                G20 = Color.green(pixelColor20);
                G21 = Color.green(pixelColor21);
                G22 = Color.green(pixelColor22);
                G23 = Color.green(pixelColor23);
                G24 = Color.green(pixelColor24);
                G25 = Color.green(pixelColor25);
                G26 = Color.green(pixelColor26);
                G27 = Color.green(pixelColor27);
                G28 = Color.green(pixelColor28);
                G29 = Color.green(pixelColor29);
                G30 = Color.green(pixelColor30);
                G31 = Color.green(pixelColor31);
                G32 = Color.green(pixelColor32);
                G33 = Color.green(pixelColor33);
                G34 = Color.green(pixelColor34);
                G35 = Color.green(pixelColor35);
                G36 = Color.green(pixelColor36);
                G37 = Color.green(pixelColor37);
                G38 = Color.green(pixelColor38);
                G39 = Color.green(pixelColor39);
                G40 = Color.green(pixelColor40);
                G41 = Color.green(pixelColor41);
                G42 = Color.green(pixelColor42);
                G43 = Color.green(pixelColor43);
                G44 = Color.green(pixelColor44);
                G45 = Color.green(pixelColor45);
                G46 = Color.green(pixelColor46);
                G47 = Color.green(pixelColor47);
                G48 = Color.green(pixelColor48);
                G49 = Color.green(pixelColor49);
                G50 = Color.green(pixelColor50);
                G51 = Color.green(pixelColor51);
                G52 = Color.green(pixelColor52);
                G53 = Color.green(pixelColor53);
                G54 = Color.green(pixelColor54);
                G55 = Color.green(pixelColor55);
                G56 = Color.green(pixelColor56);
                G57 = Color.green(pixelColor57);
                G58 = Color.green(pixelColor58);
                G59 = Color.green(pixelColor59);
                G60 = Color.green(pixelColor50);
                G61 = Color.green(pixelColor51);
                G62 = Color.green(pixelColor52);
                G63 = Color.green(pixelColor53);
                G64 = Color.green(pixelColor54);

                B = Color.blue(pixelColor);
                B1 = Color.blue(pixelColor1);
                B2 = Color.blue(pixelColor2);
                B3 = Color.blue(pixelColor3);
                B4 = Color.blue(pixelColor4);
                B5 = Color.blue(pixelColor5);
                B6 = Color.blue(pixelColor6);
                B7 = Color.blue(pixelColor7);
                B8 = Color.blue(pixelColor8);
                B9 = Color.blue(pixelColor9);
                B10 = Color.blue(pixelColor10);
                B11 = Color.blue(pixelColor11);
                B12 = Color.blue(pixelColor12);
                B13 = Color.blue(pixelColor13);
                B14 = Color.blue(pixelColor14);
                B15 = Color.blue(pixelColor15);
                B16 = Color.blue(pixelColor16);
                B17 = Color.blue(pixelColor17);
                B18 = Color.blue(pixelColor18);
                B19 = Color.blue(pixelColor19);
                B20 = Color.blue(pixelColor20);
                B21 = Color.blue(pixelColor21);
                B22 = Color.blue(pixelColor22);
                B23 = Color.blue(pixelColor23);
                B24 = Color.blue(pixelColor24);
                B25 = Color.blue(pixelColor25);
                B26 = Color.blue(pixelColor26);
                B27 = Color.blue(pixelColor27);
                B28 = Color.blue(pixelColor28);
                B29 = Color.blue(pixelColor29);
                B30 = Color.blue(pixelColor30);
                B31 = Color.blue(pixelColor31);
                B32 = Color.blue(pixelColor32);
                B33 = Color.blue(pixelColor33);
                B34 = Color.blue(pixelColor34);
                B35 = Color.blue(pixelColor35);
                B36 = Color.blue(pixelColor36);
                B37 = Color.blue(pixelColor37);
                B38 = Color.blue(pixelColor38);
                B39 = Color.blue(pixelColor39);
                B40 = Color.blue(pixelColor40);
                B41 = Color.blue(pixelColor41);
                B42 = Color.blue(pixelColor42);
                B43 = Color.blue(pixelColor43);
                B44 = Color.blue(pixelColor44);
                B45 = Color.blue(pixelColor45);
                B46 = Color.blue(pixelColor46);
                B47 = Color.blue(pixelColor47);
                B48 = Color.blue(pixelColor48);
                B49 = Color.blue(pixelColor49);
                B50 = Color.blue(pixelColor50);
                B51 = Color.blue(pixelColor51);
                B52 = Color.blue(pixelColor52);
                B53 = Color.blue(pixelColor53);
                B54 = Color.blue(pixelColor54);
                B55 = Color.blue(pixelColor55);
                B56 = Color.blue(pixelColor56);
                B57 = Color.blue(pixelColor57);
                B58 = Color.blue(pixelColor58);
                B59 = Color.blue(pixelColor59);
                B60 = Color.blue(pixelColor50);
                B61 = Color.blue(pixelColor51);
                B62 = Color.blue(pixelColor52);
                B63 = Color.blue(pixelColor53);
                B64 = Color.blue(pixelColor54);


                    if ((R == 255) && (G == 255) && (B == 255) &&
                            (R6 == 255) && (G6 == 255) && (B6 == 255) &&
                            (R12 == 255) && (G12 == 255) && (B12 == 255) &&
                            (R18 == 255) && (G18 == 255) && (B18 == 255) &&
                            (R25 == 0)&&(G25 == 0) && (B25 == 0) &&
                            (R26 == 0) && (G26 == 0) && (B26 == 0) &&
                            (R27 == 0) && (G27 == 0) && (B27 == 0)) {

                        if (countTop == 1) {
                            break;
                        }
                        cornerTopX = x;
                        cornerTopY = y;
                        countTop++;
                    }
            }
        }
        for (int x = 10; x < width - 10; x++) {
            for (int y = height - 11; y >= 10; y--) {
                pixelColor = src.getPixel(x, y);
                pixelColor1 = src.getPixel(x + 1, y);
                pixelColor2 = src.getPixel(x + 2, y);
                pixelColor3 = src.getPixel(x + 3, y);
                pixelColor4 = src.getPixel(x + 4, y);
                pixelColor5 = src.getPixel(x, y - 1);
                pixelColor6 = src.getPixel(x + 1, y - 1);
                pixelColor7 = src.getPixel(x + 2, y - 1);
                pixelColor8 = src.getPixel(x + 3, y - 1);
                pixelColor9 = src.getPixel(x + 4, y - 1);
                pixelColor10 = src.getPixel(x, y - 2);
                pixelColor11 = src.getPixel(x + 1, y - 2);
                pixelColor12 = src.getPixel(x + 2, y - 2);
                pixelColor13 = src.getPixel(x + 3, y - 2);
                pixelColor14 = src.getPixel(x + 4, y - 2);
                pixelColor15 = src.getPixel(x, y - 3);
                pixelColor16 = src.getPixel(x + 1, y - 3);
                pixelColor17 = src.getPixel(x + 2, y - 3);
                pixelColor18 = src.getPixel(x + 3, y - 3);
                pixelColor19 = src.getPixel(x + 4, y - 3);
                pixelColor20 = src.getPixel(x, y - 4);
                pixelColor21 = src.getPixel(x + 1, y - 4);
                pixelColor22 = src.getPixel(x + 2, y - 4);
                pixelColor23 = src.getPixel(x + 3, y - 4);
                pixelColor24 = src.getPixel(x + 4, y - 4);
                pixelColor25 = src.getPixel(x, y + 2);
                pixelColor26 = src.getPixel(x + 1, y + 2);
                pixelColor27 = src.getPixel(x + 2, y + 2);
                pixelColor28 = src.getPixel(x + 3, y + 2);
                pixelColor29 = src.getPixel(x + 4, y + 2);
                pixelColor30 = src.getPixel(x, y - 2);
                pixelColor31 = src.getPixel(x + 1, y - 2);
                pixelColor32 = src.getPixel(x + 2, y - 2);
                pixelColor33 = src.getPixel(x + 3, y - 2);
                pixelColor34 = src.getPixel(x + 4, y - 2);
                pixelColor35 = src.getPixel(x, y - 3);
                pixelColor36 = src.getPixel(x + 1, y - 3);
                pixelColor37 = src.getPixel(x + 2, y - 3);
                pixelColor38 = src.getPixel(x + 3, y - 3);
                pixelColor39 = src.getPixel(x + 4, y - 3);
                pixelColor40 = src.getPixel(x, y - 4);
                pixelColor41 = src.getPixel(x + 1, y - 4);
                pixelColor42 = src.getPixel(x + 2, y - 4);
                pixelColor43 = src.getPixel(x + 3, y - 4);
                pixelColor44 = src.getPixel(x + 4, y - 4);
                pixelColor45 = src.getPixel(x - 3, y + 2);
                pixelColor46 = src.getPixel(x - 2, y + 2);
                pixelColor47 = src.getPixel(x - 1, y + 2);
                pixelColor48 = src.getPixel(x, y + 2);
                pixelColor49 = src.getPixel(x + 1, y + 2);
                pixelColor50 = src.getPixel(x + 2, y + 2);
                pixelColor51 = src.getPixel(x + 3, y + 2);
                pixelColor52 = src.getPixel(x + 4, y + 2);
                pixelColor53 = src.getPixel(x + 5, y + 2);
                pixelColor54 = src.getPixel(x + 6, y + 2);
                pixelColor55 = src.getPixel(x + 7, y + 2);
                pixelColor56 = src.getPixel(x - 4, y + 2);
                pixelColor57 = src.getPixel(x - 4, y + 1);
                pixelColor58 = src.getPixel(x - 4, y);
                pixelColor59 = src.getPixel(x - 4, y - 1);
                pixelColor60 = src.getPixel(x - 4, y - 2);
                pixelColor61 = src.getPixel(x - 4, y - 3);
                pixelColor62 = src.getPixel(x - 4, y - 4);
                pixelColor63 = src.getPixel(x - 4, y - 5);
                pixelColor64 = src.getPixel(x - 4, y - 6);

                R = Color.red(pixelColor);
                R1 = Color.red(pixelColor1);
                R2 = Color.red(pixelColor2);
                R3 = Color.red(pixelColor3);
                R4 = Color.red(pixelColor4);
                R5 = Color.red(pixelColor5);
                R6 = Color.red(pixelColor6);
                R7 = Color.red(pixelColor7);
                R8 = Color.red(pixelColor8);
                R9 = Color.red(pixelColor9);
                R10 = Color.red(pixelColor10);
                R11 = Color.red(pixelColor11);
                R12 = Color.red(pixelColor12);
                R13 = Color.red(pixelColor13);
                R14 = Color.red(pixelColor14);
                R15 = Color.red(pixelColor15);
                R16 = Color.red(pixelColor16);
                R17 = Color.red(pixelColor17);
                R18 = Color.red(pixelColor18);
                R19 = Color.red(pixelColor19);
                R20 = Color.red(pixelColor20);
                R21 = Color.red(pixelColor21);
                R22 = Color.red(pixelColor22);
                R23 = Color.red(pixelColor23);
                R24 = Color.red(pixelColor24);
                R25 = Color.red(pixelColor25);
                R26 = Color.red(pixelColor26);
                R27 = Color.red(pixelColor27);
                R28 = Color.red(pixelColor28);
                R29 = Color.red(pixelColor29);
                R30 = Color.red(pixelColor30);
                R31 = Color.red(pixelColor31);
                R32 = Color.red(pixelColor32);
                R33 = Color.red(pixelColor33);
                R34 = Color.red(pixelColor34);
                R35 = Color.red(pixelColor35);
                R36 = Color.red(pixelColor36);
                R37 = Color.red(pixelColor37);
                R38 = Color.red(pixelColor38);
                R39 = Color.red(pixelColor39);
                R40 = Color.red(pixelColor40);
                R41 = Color.red(pixelColor41);
                R42 = Color.red(pixelColor42);
                R43 = Color.red(pixelColor43);
                R44 = Color.red(pixelColor44);
                R45 = Color.red(pixelColor45);
                R46 = Color.red(pixelColor46);
                R47 = Color.red(pixelColor47);
                R48 = Color.red(pixelColor48);
                R49 = Color.red(pixelColor49);
                R50 = Color.red(pixelColor50);
                R51 = Color.red(pixelColor51);
                R52 = Color.red(pixelColor52);
                R53 = Color.red(pixelColor53);
                R54 = Color.red(pixelColor54);
                R55 = Color.red(pixelColor55);
                R56 = Color.red(pixelColor56);
                R57 = Color.red(pixelColor57);
                R58 = Color.red(pixelColor58);
                R59 = Color.red(pixelColor59);
                R60 = Color.red(pixelColor50);
                R61 = Color.red(pixelColor51);
                R62 = Color.red(pixelColor52);
                R63 = Color.red(pixelColor53);
                R64 = Color.red(pixelColor54);

                G = Color.green(pixelColor);
                G1 = Color.green(pixelColor1);
                G2 = Color.green(pixelColor2);
                G3 = Color.green(pixelColor3);
                G4 = Color.green(pixelColor4);
                G5 = Color.green(pixelColor5);
                G6 = Color.green(pixelColor6);
                G7 = Color.green(pixelColor7);
                G8 = Color.green(pixelColor8);
                G9 = Color.green(pixelColor9);
                G10 = Color.green(pixelColor10);
                G11 = Color.green(pixelColor11);
                G12 = Color.green(pixelColor12);
                G13 = Color.green(pixelColor13);
                G14 = Color.green(pixelColor14);
                G15 = Color.green(pixelColor15);
                G16 = Color.green(pixelColor16);
                G17 = Color.green(pixelColor17);
                G18 = Color.green(pixelColor18);
                G19 = Color.green(pixelColor19);
                G20 = Color.green(pixelColor20);
                G21 = Color.green(pixelColor21);
                G22 = Color.green(pixelColor22);
                G23 = Color.green(pixelColor23);
                G24 = Color.green(pixelColor24);
                G25 = Color.green(pixelColor25);
                G26 = Color.green(pixelColor26);
                G27 = Color.green(pixelColor27);
                G28 = Color.green(pixelColor28);
                G29 = Color.green(pixelColor29);
                G30 = Color.green(pixelColor30);
                G31 = Color.green(pixelColor31);
                G32 = Color.green(pixelColor32);
                G33 = Color.green(pixelColor33);
                G34 = Color.green(pixelColor34);
                G35 = Color.green(pixelColor35);
                G36 = Color.green(pixelColor36);
                G37 = Color.green(pixelColor37);
                G38 = Color.green(pixelColor38);
                G39 = Color.green(pixelColor39);
                G40 = Color.green(pixelColor40);
                G41 = Color.green(pixelColor41);
                G42 = Color.green(pixelColor42);
                G43 = Color.green(pixelColor43);
                G44 = Color.green(pixelColor44);
                G45 = Color.green(pixelColor45);
                G46 = Color.green(pixelColor46);
                G47 = Color.green(pixelColor47);
                G48 = Color.green(pixelColor48);
                G49 = Color.green(pixelColor49);
                G50 = Color.green(pixelColor50);
                G51 = Color.green(pixelColor51);
                G52 = Color.green(pixelColor52);
                G53 = Color.green(pixelColor53);
                G54 = Color.green(pixelColor54);
                G55 = Color.green(pixelColor55);
                G56 = Color.green(pixelColor56);
                G57 = Color.green(pixelColor57);
                G58 = Color.green(pixelColor58);
                G59 = Color.green(pixelColor59);
                G60 = Color.green(pixelColor50);
                G61 = Color.green(pixelColor51);
                G62 = Color.green(pixelColor52);
                G63 = Color.green(pixelColor53);
                G64 = Color.green(pixelColor54);

                B = Color.blue(pixelColor);
                B1 = Color.blue(pixelColor1);
                B2 = Color.blue(pixelColor2);
                B3 = Color.blue(pixelColor3);
                B4 = Color.blue(pixelColor4);
                B5 = Color.blue(pixelColor5);
                B6 = Color.blue(pixelColor6);
                B7 = Color.blue(pixelColor7);
                B8 = Color.blue(pixelColor8);
                B9 = Color.blue(pixelColor9);
                B10 = Color.blue(pixelColor10);
                B11 = Color.blue(pixelColor11);
                B12 = Color.blue(pixelColor12);
                B13 = Color.blue(pixelColor13);
                B14 = Color.blue(pixelColor14);
                B15 = Color.blue(pixelColor15);
                B16 = Color.blue(pixelColor16);
                B17 = Color.blue(pixelColor17);
                B18 = Color.blue(pixelColor18);
                B19 = Color.blue(pixelColor19);
                B20 = Color.blue(pixelColor20);
                B21 = Color.blue(pixelColor21);
                B22 = Color.blue(pixelColor22);
                B23 = Color.blue(pixelColor23);
                B24 = Color.blue(pixelColor24);
                B25 = Color.blue(pixelColor25);
                B26 = Color.blue(pixelColor26);
                B27 = Color.blue(pixelColor27);
                B28 = Color.blue(pixelColor28);
                B29 = Color.blue(pixelColor29);
                B30 = Color.blue(pixelColor30);
                B31 = Color.blue(pixelColor31);
                B32 = Color.blue(pixelColor32);
                B33 = Color.blue(pixelColor33);
                B34 = Color.blue(pixelColor34);
                B35 = Color.blue(pixelColor35);
                B36 = Color.blue(pixelColor36);
                B37 = Color.blue(pixelColor37);
                B38 = Color.blue(pixelColor38);
                B39 = Color.blue(pixelColor39);
                B40 = Color.blue(pixelColor40);
                B41 = Color.blue(pixelColor41);
                B42 = Color.blue(pixelColor42);
                B43 = Color.blue(pixelColor43);
                B44 = Color.blue(pixelColor44);
                B45 = Color.blue(pixelColor45);
                B46 = Color.blue(pixelColor46);
                B47 = Color.blue(pixelColor47);
                B48 = Color.blue(pixelColor48);
                B49 = Color.blue(pixelColor49);
                B50 = Color.blue(pixelColor50);
                B51 = Color.blue(pixelColor51);
                B52 = Color.blue(pixelColor52);
                B53 = Color.blue(pixelColor53);
                B54 = Color.blue(pixelColor54);
                B55 = Color.blue(pixelColor55);
                B56 = Color.blue(pixelColor56);
                B57 = Color.blue(pixelColor57);
                B58 = Color.blue(pixelColor58);
                B59 = Color.blue(pixelColor59);
                B60 = Color.blue(pixelColor50);
                B61 = Color.blue(pixelColor51);
                B62 = Color.blue(pixelColor52);
                B63 = Color.blue(pixelColor53);
                B64 = Color.blue(pixelColor54);

                if ((R == 255) && (G == 255) && (B == 255) &&
                        (R6 == 255) && (G6 == 255) && (B6 == 255) &&
                        (R12 == 255) && (G12 == 255) && (B12 == 255) &&
                        (R18 == 255) && (G18 == 255) && (B18 == 255) &&
                        (R25 == 0) && (G25 == 0) && (B25 == 0) &&
                        (R26 == 0) && (G26 == 0) && (B26 == 0) &&
                        (R27 == 0) && (G27 == 0) && (B27 == 0)){
                    if (countBottom == 1) {
                        break;
                    }
                    cornerBottomX = x;
                    cornerBottomY = y;
                    countBottom++;
                }
            }
        }
        for (int y = 10; y < height - 10; y++) {
            for (int x = 10; x < width - 10; x++) {
                pixelColor = src.getPixel(x, y);
                pixelColor1 = src.getPixel(x - 1, y);
                pixelColor2 = src.getPixel(x + 1, y);
                pixelColor3 = src.getPixel(x - 2, y + 1);
                pixelColor4 = src.getPixel(x - 1, y + 1);
                pixelColor5 = src.getPixel(x, y + 1);
                pixelColor6 = src.getPixel(x + 1, y + 1);
                pixelColor7 = src.getPixel(x + 2, y + 1);
                pixelColor8 = src.getPixel(x - 2, y + 2);
                pixelColor9 = src.getPixel(x - 1, y + 2);
                pixelColor10 = src.getPixel(x, y + 2);
                pixelColor11 = src.getPixel(x + 1, y + 2);
                pixelColor12 = src.getPixel(x + 2, y + 2);
                pixelColor13 = src.getPixel(x - 2, y + 3);
                pixelColor14 = src.getPixel(x - 1, y + 3);
                pixelColor15 = src.getPixel(x, y + 3);
                pixelColor16 = src.getPixel(x + 1, y + 3);
                pixelColor17 = src.getPixel(x + 2, y + 3);
                pixelColor18 = src.getPixel(x - 7, y - 2);
                pixelColor19 = src.getPixel(x - 6, y - 2);
                pixelColor20 = src.getPixel(x - 5, y - 2);
                pixelColor21 = src.getPixel(x - 4, y - 2);
                pixelColor22 = src.getPixel(x - 3, y - 2);
                pixelColor23 = src.getPixel(x - 2, y - 2);
                pixelColor24 = src.getPixel(x - 1, y - 2);
                pixelColor25 = src.getPixel(x, y - 2);
                pixelColor26 = src.getPixel(x + 1, y - 2);
                pixelColor27 = src.getPixel(x + 2, y - 2);
                pixelColor28 = src.getPixel(x + 3, y - 2);
                pixelColor29 = src.getPixel(x + 4, y - 2);
                pixelColor30 = src.getPixel(x + 5, y - 2);
                pixelColor31 = src.getPixel(x + 6, y - 2);
                pixelColor32 = src.getPixel(x + 7, y - 2);
                pixelColor33 = src.getPixel(x + 8, y - 3);
                pixelColor34 = src.getPixel(x + 4, y - 2);
                pixelColor35 = src.getPixel(x, y - 3);
                pixelColor36 = src.getPixel(x + 1, y - 3);
                pixelColor37 = src.getPixel(x + 2, y - 3);
                pixelColor38 = src.getPixel(x + 3, y - 3);
                pixelColor39 = src.getPixel(x + 4, y - 3);
                pixelColor40 = src.getPixel(x, y - 4);
                pixelColor41 = src.getPixel(x + 1, y - 4);
                pixelColor42 = src.getPixel(x + 2, y - 4);
                pixelColor43 = src.getPixel(x + 3, y - 4);
                pixelColor44 = src.getPixel(x + 4, y - 4);
                pixelColor45 = src.getPixel(x - 3, y + 2);
                pixelColor46 = src.getPixel(x - 2, y + 2);
                pixelColor47 = src.getPixel(x - 1, y + 2);
                pixelColor48 = src.getPixel(x, y + 2);
                pixelColor49 = src.getPixel(x + 1, y + 2);
                pixelColor50 = src.getPixel(x + 2, y + 2);
                pixelColor51 = src.getPixel(x + 3, y + 2);
                pixelColor52 = src.getPixel(x + 4, y + 2);
                pixelColor53 = src.getPixel(x + 5, y + 2);
                pixelColor54 = src.getPixel(x + 6, y + 2);
                pixelColor55 = src.getPixel(x + 7, y + 2);
                pixelColor56 = src.getPixel(x - 4, y + 2);
                pixelColor57 = src.getPixel(x - 4, y + 1);
                pixelColor58 = src.getPixel(x - 4, y);
                pixelColor59 = src.getPixel(x - 4, y - 1);
                pixelColor60 = src.getPixel(x - 4, y - 2);
                pixelColor61 = src.getPixel(x - 4, y - 3);
                pixelColor62 = src.getPixel(x - 4, y - 4);
                pixelColor63 = src.getPixel(x - 4, y - 5);
                pixelColor64 = src.getPixel(x - 4, y - 6);


                //A = Color.alpha(pixelColor);

                R = Color.red(pixelColor);
                R1 = Color.red(pixelColor1);
                R2 = Color.red(pixelColor2);
                R3 = Color.red(pixelColor3);
                R4 = Color.red(pixelColor4);
                R5 = Color.red(pixelColor5);
                R6 = Color.red(pixelColor6);
                R7 = Color.red(pixelColor7);
                R8 = Color.red(pixelColor8);
                R9 = Color.red(pixelColor9);
                R10 = Color.red(pixelColor10);
                R11 = Color.red(pixelColor11);
                R12 = Color.red(pixelColor12);
                R13 = Color.red(pixelColor13);
                R14 = Color.red(pixelColor14);
                R15 = Color.red(pixelColor15);
                R16 = Color.red(pixelColor16);
                R17 = Color.red(pixelColor17);
                R18 = Color.red(pixelColor18);
                R19 = Color.red(pixelColor19);
                R20 = Color.red(pixelColor20);
                R21 = Color.red(pixelColor21);
                R22 = Color.red(pixelColor22);
                R23 = Color.red(pixelColor23);
                R24 = Color.red(pixelColor24);
                R25 = Color.red(pixelColor25);
                R26 = Color.red(pixelColor26);
                R27 = Color.red(pixelColor27);
                R28 = Color.red(pixelColor28);
                R29 = Color.red(pixelColor29);
                R30 = Color.red(pixelColor30);
                R31 = Color.red(pixelColor31);
                R32 = Color.red(pixelColor32);
                R33 = Color.red(pixelColor33);
                R34 = Color.red(pixelColor34);
                R35 = Color.red(pixelColor35);
                R36 = Color.red(pixelColor36);
                R37 = Color.red(pixelColor37);
                R38 = Color.red(pixelColor38);
                R39 = Color.red(pixelColor39);
                R40 = Color.red(pixelColor40);
                R41 = Color.red(pixelColor41);
                R42 = Color.red(pixelColor42);
                R43 = Color.red(pixelColor43);
                R44 = Color.red(pixelColor44);
                R45 = Color.red(pixelColor45);
                R46 = Color.red(pixelColor46);
                R47 = Color.red(pixelColor47);
                R48 = Color.red(pixelColor48);
                R49 = Color.red(pixelColor49);
                R50 = Color.red(pixelColor50);
                R51 = Color.red(pixelColor51);
                R52 = Color.red(pixelColor52);
                R53 = Color.red(pixelColor53);
                R54 = Color.red(pixelColor54);
                R55 = Color.red(pixelColor55);
                R56 = Color.red(pixelColor56);
                R57 = Color.red(pixelColor57);
                R58 = Color.red(pixelColor58);
                R59 = Color.red(pixelColor59);
                R60 = Color.red(pixelColor50);
                R61 = Color.red(pixelColor51);
                R62 = Color.red(pixelColor52);
                R63 = Color.red(pixelColor53);
                R64 = Color.red(pixelColor54);

                G = Color.green(pixelColor);
                G1 = Color.green(pixelColor1);
                G2 = Color.green(pixelColor2);
                G3 = Color.green(pixelColor3);
                G4 = Color.green(pixelColor4);
                G5 = Color.green(pixelColor5);
                G6 = Color.green(pixelColor6);
                G7 = Color.green(pixelColor7);
                G8 = Color.green(pixelColor8);
                G9 = Color.green(pixelColor9);
                G10 = Color.green(pixelColor10);
                G11 = Color.green(pixelColor11);
                G12 = Color.green(pixelColor12);
                G13 = Color.green(pixelColor13);
                G14 = Color.green(pixelColor14);
                G15 = Color.green(pixelColor15);
                G16 = Color.green(pixelColor16);
                G17 = Color.green(pixelColor17);
                G18 = Color.green(pixelColor18);
                G19 = Color.green(pixelColor19);
                G20 = Color.green(pixelColor20);
                G21 = Color.green(pixelColor21);
                G22 = Color.green(pixelColor22);
                G23 = Color.green(pixelColor23);
                G24 = Color.green(pixelColor24);
                G25 = Color.green(pixelColor25);
                G26 = Color.green(pixelColor26);
                G27 = Color.green(pixelColor27);
                G28 = Color.green(pixelColor28);
                G29 = Color.green(pixelColor29);
                G30 = Color.green(pixelColor30);
                G31 = Color.green(pixelColor31);
                G32 = Color.green(pixelColor32);
                G33 = Color.green(pixelColor33);
                G34 = Color.green(pixelColor34);
                G35 = Color.green(pixelColor35);
                G36 = Color.green(pixelColor36);
                G37 = Color.green(pixelColor37);
                G38 = Color.green(pixelColor38);
                G39 = Color.green(pixelColor39);
                G40 = Color.green(pixelColor40);
                G41 = Color.green(pixelColor41);
                G42 = Color.green(pixelColor42);
                G43 = Color.green(pixelColor43);
                G44 = Color.green(pixelColor44);
                G45 = Color.green(pixelColor45);
                G46 = Color.green(pixelColor46);
                G47 = Color.green(pixelColor47);
                G48 = Color.green(pixelColor48);
                G49 = Color.green(pixelColor49);
                G50 = Color.green(pixelColor50);
                G51 = Color.green(pixelColor51);
                G52 = Color.green(pixelColor52);
                G53 = Color.green(pixelColor53);
                G54 = Color.green(pixelColor54);
                G55 = Color.green(pixelColor55);
                G56 = Color.green(pixelColor56);
                G57 = Color.green(pixelColor57);
                G58 = Color.green(pixelColor58);
                G59 = Color.green(pixelColor59);
                G60 = Color.green(pixelColor50);
                G61 = Color.green(pixelColor51);
                G62 = Color.green(pixelColor52);
                G63 = Color.green(pixelColor53);
                G64 = Color.green(pixelColor54);

                B = Color.blue(pixelColor);
                B1 = Color.blue(pixelColor1);
                B2 = Color.blue(pixelColor2);
                B3 = Color.blue(pixelColor3);
                B4 = Color.blue(pixelColor4);
                B5 = Color.blue(pixelColor5);
                B6 = Color.blue(pixelColor6);
                B7 = Color.blue(pixelColor7);
                B8 = Color.blue(pixelColor8);
                B9 = Color.blue(pixelColor9);
                B10 = Color.blue(pixelColor10);
                B11 = Color.blue(pixelColor11);
                B12 = Color.blue(pixelColor12);
                B13 = Color.blue(pixelColor13);
                B14 = Color.blue(pixelColor14);
                B15 = Color.blue(pixelColor15);
                B16 = Color.blue(pixelColor16);
                B17 = Color.blue(pixelColor17);
                B18 = Color.blue(pixelColor18);
                B19 = Color.blue(pixelColor19);
                B20 = Color.blue(pixelColor20);
                B21 = Color.blue(pixelColor21);
                B22 = Color.blue(pixelColor22);
                B23 = Color.blue(pixelColor23);
                B24 = Color.blue(pixelColor24);
                B25 = Color.blue(pixelColor25);
                B26 = Color.blue(pixelColor26);
                B27 = Color.blue(pixelColor27);
                B28 = Color.blue(pixelColor28);
                B29 = Color.blue(pixelColor29);
                B30 = Color.blue(pixelColor30);
                B31 = Color.blue(pixelColor31);
                B32 = Color.blue(pixelColor32);
                B33 = Color.blue(pixelColor33);
                B34 = Color.blue(pixelColor34);
                B35 = Color.blue(pixelColor35);
                B36 = Color.blue(pixelColor36);
                B37 = Color.blue(pixelColor37);
                B38 = Color.blue(pixelColor38);
                B39 = Color.blue(pixelColor39);
                B40 = Color.blue(pixelColor40);
                B41 = Color.blue(pixelColor41);
                B42 = Color.blue(pixelColor42);
                B43 = Color.blue(pixelColor43);
                B44 = Color.blue(pixelColor44);
                B45 = Color.blue(pixelColor45);
                B46 = Color.blue(pixelColor46);
                B47 = Color.blue(pixelColor47);
                B48 = Color.blue(pixelColor48);
                B49 = Color.blue(pixelColor49);
                B50 = Color.blue(pixelColor50);
                B51 = Color.blue(pixelColor51);
                B52 = Color.blue(pixelColor52);
                B53 = Color.blue(pixelColor53);
                B54 = Color.blue(pixelColor54);
                B55 = Color.blue(pixelColor55);
                B56 = Color.blue(pixelColor56);
                B57 = Color.blue(pixelColor57);
                B58 = Color.blue(pixelColor58);
                B59 = Color.blue(pixelColor59);
                B60 = Color.blue(pixelColor50);
                B61 = Color.blue(pixelColor51);
                B62 = Color.blue(pixelColor52);
                B63 = Color.blue(pixelColor53);
                B64 = Color.blue(pixelColor54);

                /*if ((R < 75) && (G < 85) && (B < 125) &&
                        (R5 < 75) && (G5 < 85) && (B5 < 125) &&
                        (R10 < 75) && (G10 < 85) && (B10 < 125) &&
                        (R15 < 75) && (G15 < 85) && (B15 < 125) &&
                        (R22 > 150) && (G22 > 145) && (B22 > 145) &&
                        (R23 > 150) && (G23 > 145) && (B23 > 145) &&
                        (R24 > 150) && (G24 > 145) && (B24 > 145) &&
                        (R25 > 150) && (G25 > 145) && (B25 > 145) &&
                        (R27 > 150) && (G27 > 145) && (B27 > 145) &&
                        (R28 > 150) && (G28 > 145) && (B28 > 145)) {*/
                    if ((R == 0) && (R5 == 0) &&
                            (R10 == 0) &&
                            (R15 == 0) &&
                            (R22 == 255) &&
                            (R23 == 255) &&
                            (R24 == 255) &&
                            (R25 == 255) &&
                            (R27 == 255) &&
                            (R28 == 255)){

                    if(countLegTop == 1){
                        break;
                    }
                    legTopX = x;
                    legTopY = y;
                    countLegTop++;
                }
            }
        }
        for(int x = cornerBottomX, y = height - 1; y >= 0; y-- ){
            pixelColor = src.getPixel(x, y);
            pixelColor1 = src.getPixel(x + 1, y - 1);
            pixelColor2 = src.getPixel(x + 2, y - 2);
            pixelColor3 = src.getPixel(x + 3, y - 3);
            pixelColor4 = src.getPixel(x, y + 1);
            pixelColor5 = src.getPixel(x + 1, y + 1);
            pixelColor6 = src.getPixel(x + 2, y + 1);
            pixelColor7 = src.getPixel(x + 2, y + 1);
            pixelColor8 = src.getPixel(x - 2, y + 2);

            R = Color.red(pixelColor);
            R1 = Color.red(pixelColor1);
            R2 = Color.red(pixelColor2);
            R3 = Color.red(pixelColor3);
            R4 = Color.red(pixelColor4);
            R5 = Color.red(pixelColor5);
            R6 = Color.red(pixelColor6);
            R7 = Color.red(pixelColor7);
            R8 = Color.red(pixelColor8);

            G = Color.green(pixelColor);
            G1 = Color.green(pixelColor1);
            G2 = Color.green(pixelColor2);
            G3 = Color.green(pixelColor3);
            G4 = Color.green(pixelColor4);
            G5 = Color.green(pixelColor5);
            G6 = Color.green(pixelColor6);
            G7 = Color.green(pixelColor7);
            G8 = Color.green(pixelColor8);

            B = Color.blue(pixelColor);
            B1 = Color.blue(pixelColor1);
            B2 = Color.blue(pixelColor2);
            B3 = Color.blue(pixelColor3);
            B4 = Color.blue(pixelColor4);
            B5 = Color.blue(pixelColor5);
            B6 = Color.blue(pixelColor6);
            B7 = Color.blue(pixelColor7);
            B8 = Color.blue(pixelColor8);
            if(R == 255 && R1 == 255 && R2 == 255 && R3 == 255 && R4 == 0 && R5 == 0 && R6 == 0){
                if(countTop2 == 1){
                    break;
                }
                legTop2X = x;
                legTop2Y = y;
                countTop2++;
            }
        }
    }

    public void onClickClearAll(View view) {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }

    public void onClickDeletePhoto(View view) {
        File file = new File(filePath);
        file.delete();
    }
}