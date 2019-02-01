package info.wutao.wtv1searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import info.wutao.v1.api.serach.ISerachService;
import info.wutao.v1.entity.Product;
import info.wutao.v1.pojo.PageResultBean;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
@Service
public class SerachServiceImpl implements ISerachService<Product> {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List listByName(String queryCriteria) {
        List<Product> list = new ArrayList<>();

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("product_name:"+queryCriteria);

        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);
            SolrDocumentList results = queryResponse.getResults();

            for (SolrDocument result : results) {
                Product product = new Product();
                product.setId((Long.parseLong(result.get("id")+"")));
                product.setName((String)result.get("product_name"));
                product.setPrice((Long.parseLong(result.get("product_price")+"")));
                product.setSalePoint((String)result.get("product_sale_point"));
                product.setImage((String)result.get("product_image"));

                list.add(product);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    @Override
    public PageResultBean<Product> page(String queryName, Integer pageNum, Integer pageSize) {
        PageResultBean<Product> pageResultBean = new PageResultBean<>();

        //组装查询结果
        SolrQuery solrQuery = new SolrQuery();
        if(queryName != null && !"".equals(queryName.trim())) {
            solrQuery.setQuery("product_name:"+queryName);
        }else {
            //用户输入信息为空，设置查询热门
            solrQuery.setQuery("product_name:手机");
        }

        //设置关键词高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        //设置分页
        solrQuery.setStart((pageNum - 1)*pageSize);
        solrQuery.setRows(pageSize);

        try {
            QueryResponse queryResponse = solrClient.query(solrQuery);

            //获取当前页数据集合
            SolrDocumentList results = queryResponse.getResults();

            //获取分页总条数
            long total = results.getNumFound();

            List<Product> list = new ArrayList<>();

            //获取高亮信息
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            for (SolrDocument result : results) {
                //需要避免数据空指针异常
                Product product = new Product();

                product.setId(Long.parseLong(result.get("id").toString()));
                product.setPrice(Long.parseLong(result.get("product_price").toString()));
                product.setSalePoint((String)result.get("product_sale_point"));
                product.setImage((String)result.get("product_image"));

                //高亮信息单独处理
                Map<String, List<String>> productNameMap = highlighting.get(result.getFieldValue("id"));
                List<String> productNameList = productNameMap.get("product_name");

                product.setName(productNameList.get(0));
                list.add(product);
            }

            pageResultBean.setPageNum(pageNum);
            pageResultBean.setPageSize(pageSize);
            pageResultBean.setTotal(total);
            pageResultBean.setPages((int) ((total % pageSize == 0) ? (total / pageSize) : ((total / pageSize) + 1)));
            pageResultBean.setList(list);

            return pageResultBean;
        } catch (SolrServerException e) {
            e.printStackTrace();
            pageResultBean.setList(new ArrayList<>());
            return pageResultBean;

        } catch (IOException e) {
            e.printStackTrace();
            pageResultBean.setList(new ArrayList<>());
            return pageResultBean;
        }
    }
}
