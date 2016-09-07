package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import cn.dreampie.orm.generate.GeneratorFactory;


/**
 * Created by wangrenhui on 14-4-17.
 */
@Table(name = "sec_token", generatedKey = "uuid", generatedType = GeneratorFactory.UUID, cached = true)
public class Token extends Model<Token> {
  public static final Token dao = new Token();

}