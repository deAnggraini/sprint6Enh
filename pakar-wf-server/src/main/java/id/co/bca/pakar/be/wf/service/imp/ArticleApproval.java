package id.co.bca.pakar.be.wf.service.imp;

import id.co.bca.pakar.be.wf.service.Delegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ArticleApproval<T> implements Delegate<T> {
    private Logger logger = LoggerFactory.getLogger(ArticleApproval.class);

    @Override
    public void execute(T obj) {

    }
}
