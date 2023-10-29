package com.fmqwd.avgmaker.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fmqwd.avgmaker.R
import com.fmqwd.avgmaker.datas.ListMainListBean
import com.fmqwd.avgmaker.pages.gamepage.GameMainPage
import com.google.android.material.snackbar.Snackbar

/**
 * @projectName
 * @author :fmqwd
 * @description:
 * @date :2023/10/28 15:42
 */


class ListMainListAdapter(
    private val mContext: Context,
    private val mListMainListBeans: ArrayList<ListMainListBean>
) : RecyclerView.Adapter<ListMainListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_main_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = mListMainListBeans[position]
        val image = bean.image
        val path = bean.path
        val id = bean.id

        holder.title.text = bean.title
        holder.name.text = bean.name

        if (image.isNotEmpty()) {
            holder.image.setImageBitmap(BitmapFactory.decodeFile(image))
        }

        holder.body.setOnClickListener {
            val intent = Intent(mContext, GameMainPage::class.java)
            intent.putExtra("path", path)
            mContext.startActivity(intent)
        }

        holder.body.setOnLongClickListener {
            showDeleteDialog(position, holder)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return mListMainListBeans.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.list_main_list_title)
        val name: TextView = itemView.findViewById(R.id.list_main_list_name)
        val image: ImageView = itemView.findViewById(R.id.list_main_list_title_img)
        val body: LinearLayout = itemView.findViewById(R.id.list_main_list_body)
    }


    private fun showDeleteDialog(position: Int, holder: ViewHolder) {
        val builder = AlertDialog.Builder(mContext)
        val layout = LinearLayout(mContext)
        layout.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(50,30,0,0)
        val checkBox = CheckBox(mContext)
        checkBox.text = "删除本地文件"
        checkBox.layoutParams = layoutParams
        layout.addView(checkBox)
        builder.setView(layout)
        builder.setTitle("项目将从库中删除")
        builder.setPositiveButton("确定") { _, _ ->
            mListMainListBeans.removeAt(position)
            notifyItemRemoved(position)
            Snackbar.make(holder.itemView, "已删除", Snackbar.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("取消") { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }
}