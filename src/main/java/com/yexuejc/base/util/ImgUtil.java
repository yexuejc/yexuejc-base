package com.yexuejc.base.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Iterator;

/**
 * 图片处理工具类
 *
 * @author maxf
 * @ClassName ImageUtil
 * @Description
 * @date 2018/9/3 15:25
 */
public class ImgUtil {

    /**
     * 将一张网络图片转化成Base64字符串
     *
     * @param imgURL 网络图片url
     * @return String Base64字符串
     */
    public static String getImageStrFromUrl(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            byte[] by = new byte[1024];
            InputStream is = getImageInputStreamFromUrl(null, imgURL);
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }

    /**
     * 读取网络图片url为输入流
     * <p>本方法未关闭io流，请自行关闭io流</p>
     * <p>is.close();</p>
     *
     * @param imgURL 网络图片url
     * @return InputStream 输入流
     * @throws IOException
     */
    public static InputStream getImageInputStreamFromUrl(Proxy proxy, String imgURL) throws IOException {
        // 创建URL
        URL url = new URL(imgURL);
        // 创建链接
        HttpURLConnection conn;
        if (null == proxy) {
            conn = (HttpURLConnection) url.openConnection();
        } else {
            // 如果有代理则通过代理下载
            conn = (HttpURLConnection) url.openConnection(proxy);
        }
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        InputStream is = conn.getInputStream();
        return is;
    }

    /**
     * 读取网络图片url为输入流
     * <p>本方法未关闭io流，请自行关闭io流</p>
     * <p>is.close();</p>
     *
     * @param imgURL
     * @return
     * @throws IOException
     */
    public static InputStream getImageInputStreamFromUrl(String imgURL) throws IOException {
        return getImageInputStreamFromUrl(null, imgURL);
    }


    /**
     * 将图片文件流转换成字节数组
     * <p>
     * 好处就是字节数组可以多次利用,而流一旦读取过一次之后就不能再使用了
     *
     * @param proxy
     * @param targetUrl
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static byte[] getByteArrayFromInputStream(Proxy proxy, String targetUrl)
            throws MalformedURLException, IOException {
        InputStream is = getImageInputStreamFromUrl(proxy, targetUrl);
        return getByteArray(is);
    }

    /**
     * 将图片文件流转换成字节数组
     * <p>
     * 好处就是字节数组可以多次利用,而流一旦读取过一次之后就不能再使用了
     *
     * @param targetUrl
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static byte[] getByteArrayFromInputStream(String targetUrl)
            throws MalformedURLException, IOException {
        return getByteArrayFromInputStream(null, targetUrl);
    }

    /**
     * 将图片文件流转换成字节数组
     *
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static byte[] getByteArray(InputStream is)
            throws MalformedURLException, IOException {
        // 把文件写到字节数组保存起来
        ByteArrayOutputStream fos = getByteArrayFromInputStream(is);
        byte[] bytes = fos.toByteArray();
        fos.close();
        return bytes;
    }

    /**
     * ByteArrayOutputStream转换成字节数组
     *
     * @param fos
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static byte[] getByteArray(ByteArrayOutputStream fos)
            throws MalformedURLException, IOException {
        // 把文件写到字节数组保存起来
        byte[] bytes = fos.toByteArray();
        fos.close();
        return bytes;
    }

    /**
     * 将图片文件流转换成ByteArrayOutputStream
     * <p>本方法未关闭io流，请自行关闭io流</p>
     * <p>is.close();</p>
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static ByteArrayOutputStream getByteArrayFromInputStream(InputStream is) throws IOException {
        // 把文件写到字节数组保存起来
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        bis.close();
        return fos;
    }

    /**
     * 如果你已经将图片文件流InputStream读取出来放到一个字节数组
     * <p>
     * 那么根据这个字节数组也是可以转换成对应的图片流，并再次获取图片基本信息
     *
     * @param imgBytes
     * @return
     */
    public static ImageInfo getImageInfoFromInputStream(byte[] imgBytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(imgBytes);
        ImageInfo image = getImageInfoFromInputStream(in);
        return image;
    }

    /**
     * 从图片文件流读取图片文件的基本信息
     *
     * @param inputStream
     * @return
     */
    public static ImageInfo getImageInfoFromInputStream(InputStream inputStream) {
        ImageInputStream imgStream = null;
        try {
            // 创建Image流
            imgStream = ImageIO.createImageInputStream(inputStream);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(imgStream);
            if (!iter.hasNext()) {
                return null;
            }
            // 读取流中一帧就可以获取到高宽以及各式
            ImageReader reader = iter.next();
            reader.setInput(imgStream, true);
            int width = reader.getWidth(0);
            int height = reader.getHeight(0);
            String type = reader.getFormatName();
            ImageInfo bean = new ImageInfo();
            bean.setWidth(width);
            bean.setHeight(height);
            bean.setType(type);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                imgStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 将byte数组以Base64方式编码为字符串
     *
     * @param bytes 待编码的byte数组
     * @return String 编码后的字符串
     */
    public static String encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * 将以Base64方式编码的字符串解码为byte数组
     *
     * @param encodeStr 待解码的字符串
     * @return byte[] 解码后的byte数组
     * @throws IOException
     */
    public static byte[] decode(String encodeStr) throws IOException {
        byte[] bt = null;
        BASE64Decoder decoder = new BASE64Decoder();
        bt = decoder.decodeBuffer(encodeStr);
        return bt;
    }

    /**
     * 将两个byte数组连接起来后，返回连接后的Byte数组
     *
     * @param front 拼接后在前面的数组
     * @param after 拼接后在后面的数组
     * @return byte[] 拼接后的数组
     */
    public static byte[] connectBytes(byte[] front, byte[] after) {
        byte[] result = new byte[front.length + after.length];
        System.arraycopy(front, 0, result, 0, after.length);
        System.arraycopy(after, 0, result, front.length, after.length);
        return result;
    }

    /**
     * 将流保存至本机或者图片服务器
     *
     * @param in      图片流
     * @param saveUrl 目标地址
     * @throws IOException
     */
    public static void saveImgFromInputStream(InputStream in, String saveUrl) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveUrl));
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        in.close();
    }


    /**
     * 将图片以Base64方式编码为字符串
     *
     * @param imgUrl 图片的绝对路径（例如：D:\\1.jpg）
     * @return String 编码后的字符串
     * @throws IOException
     */
    public static String encodeImage(String imgUrl) throws IOException {
        FileInputStream fis = new FileInputStream(imgUrl);
        byte[] rs = new byte[fis.available()];
        fis.read(rs);
        fis.close();
        return encode(rs);
    }

    /**
     * 获取图片返回bate[]
     *
     * @param imgUrl 图片的绝对路径（例如：D:\\1.jpg）
     * @return byte[]
     * @throws IOException
     */
    public static byte[] byteImage(String imgUrl) throws IOException {
        FileInputStream fis = new FileInputStream(imgUrl);
        byte[] rs = new byte[fis.available()];
        return rs;
    }

    public static class ImageInfo {
        /**
         * 图片大小
         */
        private long size = 0;
        /**
         * 图片宽度
         */
        private int width = 0;
        /**
         * 图片高度
         */
        private int height = 0;
        /**
         * 图片类型
         */
        private String type = "jpg";
        /**
         * 符号要求的图片类型
         */
        private String[] validTypes;

        /**
         * 定义一个简单方法描述该图片是否符合要求
         *
         * @param validImg
         * @return
         */
        public boolean isValidImg(ImageInfo validImg) {
            if (null == validImg) {
                return true;
            }
            return (this.getSize() <= validImg.getSize()) && (this.getWidth() <= validImg.getWidth())
                    && (this.getHeight() <= validImg.getHeight()) && isValidType(validImg);

        }

        private boolean isValidType(ImageInfo validImg) {
            if (null == validImg.getValidTypes()) {
                return true;
            }
            boolean isValid = false;
            for (String validType : validImg.getValidTypes()) {
                if (type.equalsIgnoreCase(validType)) {
                    isValid = true;
                    break;
                }
            }
            return isValid;
        }

        private String validTypeToString() {
            if (null == validTypes) {
                return "";
            }
            StringBuffer sb = new StringBuffer("[");
            for (String type : validTypes) {
                sb.append(type).append(" ");
            }
            sb.append("]");
            return sb.toString();
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String[] getValidTypes() {
            return validTypes;
        }

        public void setValidTypes(String[] validTypes) {
            this.validTypes = validTypes;
        }
    }
}
