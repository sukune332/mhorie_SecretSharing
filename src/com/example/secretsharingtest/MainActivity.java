package com.example.secretsharingtest;


import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.w3c.dom.Text;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void SharingProcess(){
		int blockLength = 0;		// �u���b�N��
		int blockLengthRest = 0;	// ���܂蒷
		byte[][] b = null;			// ����
		
		// --- �eGUI�p�[�c���擾
		// EditText�̒��g���擾
		EditText editText = (EditText) findViewById(R.id.editText1);
		// TextView���擾
		TextView t_result = (TextView) findViewById(R.id.TextView_Result);
		TextView t1 = (TextView) findViewById(R.id.TextView01);
		TextView t2 = (TextView) findViewById(R.id.TextView02);
		TextView t3 = (TextView) findViewById(R.id.TextView03);
		TextView t4 = (TextView) findViewById(R.id.TextView04);
		TextView r1 = (TextView) findViewById(R.id.TextView001);
		TextView r2 = (TextView) findViewById(R.id.TextView002);
		TextView r3 = (TextView) findViewById(R.id.TextView003);
		TextView r4 = (TextView) findViewById(R.id.TextView004);
		TextView w1 = (TextView) findViewById(R.id.TextView05);
		TextView w2 = (TextView) findViewById(R.id.TextView06);
		TextView w3 = (TextView) findViewById(R.id.TextView07);
		TextView w4 = (TextView) findViewById(R.id.TextView08);
		TextView w5 = (TextView) findViewById(R.id.TextView09);
		// ��������
		Random rd1 = new Random();
		Random rd2 = new Random();
		Random rd3 = new Random();
		Random rd4 = new Random();
		// �����pbyte�z���錾�������Ƃ�
		byte[][] rb = null;
		
		try {
			// ��������擾�Ebyte�ϊ�
			byte[] byteText = editText.getText().toString().getBytes("UTF-8");
			printBytes(byteText);

			// 4�ɕ���
			blockLength = (byteText.length / 4)+1 ; // �؂�o��byte�̒������v�Z ��+1�͂��܂���܂܂��邽��
			blockLengthRest = byteText.length % 4;	// ���܂�
			System.out.println("length:"+byteText.length);
			
			// 1�u���b�N�̒������킩�����̂ł��̒������̗������p��
			rb = new byte[4][blockLength];
			rd1.nextBytes(rb[0]);
			rd2.nextBytes(rb[1]);
			rd3.nextBytes(rb[2]);
			rd4.nextBytes(rb[3]);
			
			// �������Ĕz��Ɏ��߂Ă���
			b = new byte[4][blockLength];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < blockLength; j++) {
					b[i][j] = byteText[j + (blockLength * i)];
					System.out.println("i:"+i+"/j:"+j+"/byte:"+b[i][j]);
				}
				printBytes(b[i]);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			System.out.println("����ۂ������݂����B�����ŏI���B");
		}
		
		
		// �\�����Ă݂�
		try {
			t1.setText("���US1:" + new String(b[0], "UTF-8"));
			t2.setText("���US2:" + new String(b[1], "UTF-8"));
			t3.setText("���US3:" + new String(b[2], "UTF-8"));
			t4.setText("���US4:" + new String(b[3], "UTF-8"));
			r1.setText("����R1:" + new String(rb[0], "UTF-8"));
			r2.setText("����R2:" + new String(rb[1], "UTF-8"));
			r3.setText("����R3:" + new String(rb[2], "UTF-8"));
			r4.setText("����R4:" + new String(rb[3], "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// �r���I�_���a
		byte[][] wi = new byte[5][blockLength * 4];

		for (int j = 0; j < blockLength; j++) {
			try {
				wi[0][j + blockLength * 0] = (byte) rb[0][j];
				wi[0][j + blockLength * 1] = (byte) (rb[1][j] ^ b[3][j]);
				wi[0][j + blockLength * 2] = (byte) (rb[2][j] ^ b[2][j]);
				wi[0][j + blockLength * 3] = (byte) (rb[3][j] ^ b[1][j]);

				wi[1][j + blockLength * 0] = (byte) (rb[0][j] ^ b[0][j]);
				wi[1][j + blockLength * 1] = (byte) rb[1][j];
				wi[1][j + blockLength * 2] = (byte) (rb[2][j] ^ b[3][j]);
				wi[1][j + blockLength * 3] = (byte) (rb[3][j] ^ b[2][j]);

				wi[2][j + blockLength * 0] = (byte) (rb[0][j] ^ b[1][j]);
				wi[2][j + blockLength * 1] = (byte) (rb[1][j] ^ b[0][j]);
				wi[2][j + blockLength * 2] = (byte) (rb[2][j]);
				wi[2][j + blockLength * 3] = (byte) (rb[3][j] ^ b[3][j]);

				wi[3][j + blockLength * 0] = (byte) (rb[0][j] ^ b[2][j]);
				wi[3][j + blockLength * 1] = (byte) (rb[1][j] ^ b[1][j]);
				wi[3][j + blockLength * 2] = (byte) (rb[2][j] ^ b[0][j]);
				wi[3][j + blockLength * 3] = (byte) (rb[3][j]);

				wi[4][j + blockLength * 0] = (byte) (rb[0][j] ^ b[3][j]);
				wi[4][j + blockLength * 1] = (byte) (rb[1][j] ^ b[2][j]);
				wi[4][j + blockLength * 2] = (byte) (rb[2][j] ^ b[1][j]);
				wi[4][j + blockLength * 3] = (byte) (rb[3][j] ^ b[0][j]);

			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				System.out.println("����ۂ������݂����B�����ŏI���B");
			}
		}
		
		// XOR���\�����Ă݂�
		try {
			w1.setText("���U���W1:" + new String(wi[0], "UTF-8"));
			w2.setText("���U���W2:" + new String(wi[1], "UTF-8"));
			w3.setText("���U���W3:" + new String(wi[2], "UTF-8"));
			w4.setText("���U���W4:" + new String(wi[3], "UTF-8"));
			w5.setText("���U���W5:" + new String(wi[4], "UTF-8"));
		} catch (UnsupportedEncodingException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		// �Ƃ肠����byte�����ō��̂��Ă݂�iXOR�����j
		/*
		try {
			byte[] result = new byte[blockLength * 4];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < blockLength; j++) {
					result[j + (blockLength * i)] = b[i][j];
				}
			}
			printBytes(result);
			t_result.setText(new String(result, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// ����
		try {
			byte[] res = new byte[(blockLength + 1) * 4];
			for (int j = 0; j < blockLength; j++) {
				res[j + blockLength * 0] = (byte) (wi[3][j + blockLength * 3] ^ wi[4][j
						+ blockLength * 3]);
				res[j + blockLength * 1] = (byte) ((byte) (wi[3][j
						+ blockLength * 2] ^ wi[4][j + blockLength * 2]) ^ res[j]);
				res[j + blockLength * 2] = (byte) ((byte) (wi[3][j
						+ blockLength * 1] ^ wi[4][j + blockLength * 1]) ^ res[j
						+ blockLength * 1]);
				res[j + blockLength * 3] = (byte) ((byte) (wi[3][j
						+ blockLength * 0] ^ wi[4][j + blockLength * 0]) ^ res[j
						+ blockLength * 2]);
			}
			t_result.setText("�����㕶����:"+new String(res, "UTF-8"));
			
			// byte���l�\��
			System.out.print("S1:");
			printBytes(b[0]);
			System.out.print("S2:");
			printBytes(b[1]);
			System.out.print("S3:");
			printBytes(b[2]);
			System.out.print("S3:");
			printBytes(b[3]);
			
			System.out.print("R1:");
			printBytes(rb[0]);
			System.out.print("R2:");
			printBytes(rb[1]);
			System.out.print("R3:");
			printBytes(rb[2]);
			System.out.print("R3:");
			printBytes(rb[3]);
			
			System.out.print("W4:");
			printBytes(wi[3]);
			System.out.print("W5:");
			printBytes(wi[4]);
			
			System.out.print("SS:");
			printBytes(res);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick_button1(View view){
        SharingProcess();
    }
	
	private static void printBytes(byte[] vector) {
	    int i;
	    for(i=0; i<vector.length; i++) {
	      System.out.printf("%02X ", vector[i]);
	    }
	    System.out.println();
	  }
}
