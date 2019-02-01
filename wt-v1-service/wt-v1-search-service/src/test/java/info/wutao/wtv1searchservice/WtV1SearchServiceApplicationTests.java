package info.wutao.wtv1searchservice;

import info.wutao.v1.entity.Product;
import info.wutao.v1.mapper.ProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1SearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void contextLoads() {
        System.err.println(solrClient);
    }

    //添加
    @Test
    public void add () {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", "10003");
        solrInputDocument.addField("product_name", "我爱你中国啊");
        solrInputDocument.addField("product_price", "345");
        solrInputDocument.addField("product_sale_point", "中国");
        solrInputDocument.addField("product_image", "123");
        try {
            solrClient.add(solrInputDocument);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //搜索
    @Test
    public void Query () {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("product_name:中国");

        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            SolrDocumentList results = queryResponse.getResults();
            for (SolrDocument result : results) {
                System.err.println(result.get("id"));
                System.err.println(result.get("product_name"));
                System.err.println(result.get("product_price"));
                System.err.println(result.get("product_sale_point"));
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除
    @Test
    public void delete () {
        try {
            UpdateResponse updateResponse = solrClient.deleteById("10002");
            System.out.println(updateResponse.getRequestUrl());
            System.out.println(updateResponse.getResponse());
            System.out.println(updateResponse.getResponseHeader());
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //全量更新
    @Test
    public void initSolr(){
        //删除原先数据

        //获取数据库中数据
        List<Product> list = productMapper.getList();

        //遍历，添加进solr中
        for (Product product : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id", product.getId());
            document.setField("product_name", product.getName());
            document.setField("product_price", product.getPrice());
            document.setField("product_sale_point", product.getSalePoint());
            document.setField("product_image", product.getImage());
            try {
                solrClient.add(document);
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                solrClient.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

