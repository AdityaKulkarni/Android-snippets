/*Adapter for list containing only one text field*/
class DoctorListAdapter extends BaseAdapter {

    private ArrayList<DoctorListModel> list;
    private Context context;
    private LayoutInflater inflater;

    DoctorListAdapter(ArrayList<DoctorListModel> list, Context context){
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_component_list, viewGroup, false);
            holder.tvdoclist = (TextView)view.findViewById(R.id.tvdoclist);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tvdoclist.setText(list.get(i).getDocname());
        return view;
    }

    private class ViewHolder{
        TextView tvdoclist;
    }
}
