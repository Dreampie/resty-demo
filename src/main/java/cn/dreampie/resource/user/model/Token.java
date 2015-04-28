package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.UUID;


/**
 * Created by wangrenhui on 14-4-17.
 */
@Table(name = "sec_token", primaryKey = "uuid", cached = true)
public class Token extends Model<Token> {
  public static final Token dao = new Token();

  public boolean save() {
    this.set("uuid", UUID.randomUUID());
    return super.save();
  }

}