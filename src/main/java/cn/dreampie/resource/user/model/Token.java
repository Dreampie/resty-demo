package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;


/**
 * Created by wangrenhui on 14-4-17.
 */
@Table(name = "sec_token", generatedKey = "uuid", generated = true, cached = true)
public class Token extends Model<Token> {
  public static final Token dao = new Token();

}