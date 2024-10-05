package com.pasc.lib.demo;

import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.pasc.business.workspace.BaseConfigurableFragment;
import com.pasc.lib.workspace.bean.ConfigItem;
import com.pasc.lib.workspace.util.AssetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DemoMainFragment extends BaseConfigurableFragment {

    class Item {
        String title;
        RouteMeta routeMeta;
    }

    @Override
    protected void onReceivedConfig(ConfigItem configItem) throws JSONException {

        try {
            IRouteGroup iRouteGroup = (IRouteGroup) Class.forName("com.alibaba.android.arouter.routes.ARouter$$Group$$Demo").getConstructor().newInstance();
            LinkedHashMap<String, RouteMeta> atlas = new LinkedHashMap<>();
            iRouteGroup.loadInto(atlas);

            Set<Map.Entry<String, RouteMeta>> entries = atlas.entrySet();

            LinkedHashMap<String, List<Item>> routeMetasMap = new LinkedHashMap<>();

            for (Map.Entry<String, RouteMeta> entry : entries) {
                String key = entry.getKey();
                String[] splits = key.split("/");
                if (splits.length < 4) {
                    continue;
                }

                String groupKey = splits[2];
                List<Item> routeMetas = routeMetasMap.get(groupKey);
                if (routeMetas == null) {
                    routeMetas = new ArrayList<Item>();
                    routeMetasMap.put(groupKey, routeMetas);
                }

                Item item = new Item();
                item.title = splits[3];
                item.routeMeta = entry.getValue();

                routeMetas.add(item);
            }

            JSONArray data = new JSONArray();

            Set<Map.Entry<String, List<Item>>> entriesRouteMeta = routeMetasMap.entrySet();
            for (Map.Entry<String, List<Item>> entry : entriesRouteMeta) {
                String groupName = entry.getKey();
                String assetsJson = AssetUtils.getString(getActivity(), "configSystem/group.json");
                JSONObject jsonObject = new JSONObject(assetsJson);
                jsonObject.optJSONObject("header").put("title", groupName);

                List<Item> values = entry.getValue();
                int size = values.size();

                JSONArray items = new JSONArray();
                for (int i = 0; i < size; i++) {

                    String itemJson = AssetUtils.getString(getActivity(), "configSystem/item.json");
                    JSONObject jsonObjectItem = new JSONObject(itemJson);
                    String title = values.get(i).title;
                    jsonObjectItem.put("title", title);
                    jsonObjectItem.put("onClick", "router://com.pingan.smt/Demo/" + groupName + "/" + title);
                    items.put(jsonObjectItem);
                }

                jsonObject.put("items", items);


                data.put(jsonObject);
            }

            engine.setData(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
