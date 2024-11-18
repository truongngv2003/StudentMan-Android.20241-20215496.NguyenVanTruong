package vn.edu.hust.studentman

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageEdit.setOnClickListener {
      val dialogView = LayoutInflater.from(holder.itemView.context)
        .inflate(R.layout.student_dialog, null)

      val editHoten = dialogView.findViewById<EditText>(R.id.edit_hoten)
      val editMssv = dialogView.findViewById<EditText>(R.id.edit_mssv)

      editHoten.setText(student.studentName)
      editMssv.setText(student.studentId)

      AlertDialog.Builder(holder.itemView.context)
        .setIcon(R.drawable.baseline_edit_24)
        .setTitle("Edit student")
        .setView(dialogView)
        .setPositiveButton("OK") { _, _ ->
          student.studentName = editHoten.text.toString()
          student.studentId = editMssv.text.toString()
          notifyItemChanged(position)
        }
        .setNegativeButton("Cancel", null)
        .show()
    }

    holder.imageRemove.setOnClickListener{
      AlertDialog.Builder(holder.itemView.context)
        .setIcon(R.drawable.baseline_delete_24)
        .setTitle("Delete student")
        .setMessage("Bạn chắc chắn muốn xóa ${student.studentName}-${student.studentId}?")
        .setPositiveButton("OK") { _, _ ->
          val hoten = student.studentName
          val mssv = student.studentId
          students.removeAt(position)
          notifyDataSetChanged()
          Snackbar.make(it, "Đã xóa 1 học sinh", Snackbar.LENGTH_LONG)
            .setAction("Hoàn tác") {
              students.add(position, StudentModel(hoten, mssv))
              notifyDataSetChanged()
            }
            .show()
        }
        .setNegativeButton("Cancel", null)
        .show()
    }
  }
}