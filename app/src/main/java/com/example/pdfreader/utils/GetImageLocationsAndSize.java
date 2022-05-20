package com.example.pdfreader.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import com.tom_roush.pdfbox.contentstream.PDFStreamEngine;
import com.tom_roush.pdfbox.contentstream.operator.DrawObject;
import com.tom_roush.pdfbox.contentstream.operator.Operator;
import com.tom_roush.pdfbox.contentstream.operator.state.*;
import com.tom_roush.pdfbox.cos.COSBase;
import com.tom_roush.pdfbox.cos.COSName;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle;
import com.tom_roush.pdfbox.pdmodel.graphics.PDXObject;
import com.tom_roush.pdfbox.pdmodel.graphics.form.PDFormXObject;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.tom_roush.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import com.tom_roush.pdfbox.util.Matrix;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * This is an example on how to get the x/y coordinates of image location and size of image.
 */
public class GetImageLocationsAndSize extends PDFStreamEngine
{
    Uri uri;
    File file;
    InputStream inputStream;
    ContentResolver contentResolver;
    Context context;

    /**
     * @throws IOException If there is an error loading text stripper properties.
     */
    public GetImageLocationsAndSize() throws IOException
    {
        // preparing PDFStreamEngine
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());

    }

    /**
     * @throws IOException If there is an error parsing the document.
     */

    public GetImageLocationsAndSize(Uri uri, ContentResolver contentResolver, Context context) {
        this.uri = uri;
        this.contentResolver = contentResolver;
        this.context = context;
    }

    public void getImages() {
        PDDocument document = null;
        try
        {
            PDFBoxResourceLoader.init(context);
            inputStream = contentResolver.openInputStream(uri);
            document = PDDocument.load(inputStream);

            PDPage pageHere = (PDPage)document.getPage(1);
            List annots = pageHere.getAnnotations();
            PDAnnotationTextMarkup markup = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);

            markup.setRectangle(new PDRectangle(10f,10f,30f,20f));


            //markup.setQuadPoints(1f,2f,3f,4f,5f,6f,8f,9f)
            annots.add(markup);

            GetImageLocationsAndSize printer = new GetImageLocationsAndSize();
            //PDPageContentStream contentStream = new PDPageContentStream(document,document.getPage(0));
//            contentStream.setNonStrokingColor(Color.BLUE);
//            contentStream.addRect(20,20,12,12);
//            contentStream.fill();
//            contentStream.close();
            int pageNum = 0;
            for( PDPage page : document.getPages() )
            {
                pageNum++;
                System.out.println( "\n\nProcessing page: " + pageNum +"\n---------------------------------");
                printer.processPage(page);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            if( document != null )
            {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException
    {

        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            COSName objectName = (COSName) operands.get( 0 );
            // get the PDF object
            PDXObject xobject = getResources().getXObject( objectName );
            // check if the object is an image object
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                System.out.println("\nImage [" + objectName.getName() + "]");

                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                float imageXScale = ctmNew.getScalingFactorX();
                float imageYScale = ctmNew.getScalingFactorY();

                // position of image in the pdf in terms of user space units

                System.out.println("position in PDF = " + ctmNew.getTranslateX() + ", " + ctmNew.getTranslateY() + " in user space units");

                // raw size in pixels
                System.out.println("raw image size  = " + imageWidth + ", " + imageHeight + " in pixels");

                // displayed size in user space units
                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in user space units");
            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }

}
