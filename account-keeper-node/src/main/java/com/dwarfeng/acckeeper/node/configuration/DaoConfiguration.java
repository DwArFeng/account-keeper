package com.dwarfeng.acckeeper.node.configuration;

import com.dwarfeng.acckeeper.impl.bean.entity.HibernateAccount;
import com.dwarfeng.acckeeper.impl.dao.preset.AccountPresetCriteriaMaker;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.impl.dao.HibernateBatchBaseDao;
import com.dwarfeng.subgrade.impl.dao.HibernateEntireLookupDao;
import com.dwarfeng.subgrade.impl.dao.HibernatePresetLookupDao;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;

@Configuration
public class DaoConfiguration {

    @Autowired
    private HibernateTemplate template;
    @Autowired
    private BeanTransformerConfiguration beanTransformerConfiguration;
    @Autowired
    private AccountPresetCriteriaMaker accountPresetCriteriaMaker;

    @Bean
    public HibernateBatchBaseDao<StringIdKey, HibernateStringIdKey, Account, HibernateAccount> accountHibernateBatchBaseDao() {
        return new HibernateBatchBaseDao<>(
                template,
                beanTransformerConfiguration.stringIdKeyBeanTransformer(),
                beanTransformerConfiguration.accountBeanTransformer(),
                HibernateAccount.class
        );
    }

    @Bean
    public HibernateEntireLookupDao<Account, HibernateAccount> accountHibernateEntireLookupDao() {
        return new HibernateEntireLookupDao<>(
                template,
                beanTransformerConfiguration.accountBeanTransformer(),
                HibernateAccount.class
        );
    }

    @Bean
    public HibernatePresetLookupDao<Account, HibernateAccount> accountHibernatePresetLookupDao() {
        return new HibernatePresetLookupDao<>(
                template,
                beanTransformerConfiguration.accountBeanTransformer(),
                HibernateAccount.class,
                accountPresetCriteriaMaker
        );
    }
}
