package com.fmqwd.avgmaker.adapters

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.fmqwd.avgmaker.R
import com.fmqwd.avgmaker.database.DatabaseManager
import com.fmqwd.avgmaker.datas.GlobalData
import com.fmqwd.avgmaker.datas.ListMainListBean
import com.fmqwd.avgmaker.pages.gamepage.GameMainPage
import com.fmqwd.avgmaker.utils.UIUtils.dip2px
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
            showOuterDialog(position, holder)
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


    private fun showOuterDialog(position: Int, holder: ViewHolder) {
        val outerLayout = LinearLayout(mContext).apply {
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL
            val outerLayoutParams = LinearLayout.LayoutParams(
                dip2px(mContext, 150f),
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dip2px(mContext, 5f), 0, dip2px(mContext, 5f))
            }
            val detailButton = createButton("详细", outerLayoutParams) {
                // TODO
            }
            val pathButton = createButton("编辑", outerLayoutParams) {
                // TODO
            }
            val deleteButton = createButton("删除", outerLayoutParams) {
                showDeleteDialog(position, holder)
            }
            addView(detailButton)
            addView(pathButton)
            addView(deleteButton)
        }

        val outerDialog = AlertDialog.Builder(mContext)
            .setView(outerLayout)
            .setTitle("选项")
            .create()

        outerDialog.show()
        val window = outerDialog.window
        val params = window?.attributes
        params?.width = dip2px(mContext, 250f)
        window?.attributes = params
    }

    private fun createButton(
        text: String,
        layoutParams: LinearLayout.LayoutParams,
        onClick: () -> Unit
    ): Button {
        return Button(mContext).apply {
            this.text = text
            this.layoutParams = layoutParams
            this.background = AppCompatResources.getDrawable(mContext, R.drawable.bg_main_list_info)
            setOnClickListener { onClick() }
        }
    }

    private fun showDeleteDialog(position: Int, holder: ViewHolder) {
        val layout = LinearLayout(mContext).apply {
            orientation = LinearLayout.VERTICAL
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(50, 30, 0, 0)
            }
            val checkBox = CheckBox(mContext).apply {
                text = "删除本地文件"
                this.layoutParams = layoutParams
            }
            addView(checkBox)
        }

        val deleteDialog = AlertDialog.Builder(mContext)
            .setView(layout)
            .setTitle("项目将从库中删除")
            .setPositiveButton("确定") { _, _ ->
                deleteData(position, holder)
            }
            .setNegativeButton("取消") { dialog, _ -> dialog.cancel() }
            .create()

        deleteDialog.show()
    }

    private fun deleteData(position: Int, holder: ViewHolder) {
        val item = mListMainListBeans.removeAt(position)
        GlobalData.removeIntoMainList(item)
        val database = DatabaseManager.getDatabase()
        val mainListDao = database.mainListDataDao()
        mainListDao.delete(item.toMainListData())
        notifyItemRemoved(position)
        Snackbar.make(holder.itemView, "已删除", Snackbar.LENGTH_SHORT).show()
    }

}