package com.example.qrattendance.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.qrattendance.Attendee
import com.example.qrattendance.databinding.AttendeeItemBinding

class AttendeesAdapter : RecyclerView.Adapter<AttendeesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            AttendeeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentAttendee = differ.currentList[position]
        holder.bind(currentAttendee)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class NoteViewHolder(private val binding: AttendeeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attendee: Attendee) {
            binding.apply {
                txtAttendeeName.text = attendee.name
                txtAttendeeNotes.text = attendee.notes
                txtAttendanceTime.text = attendee.time
            }
        }

    }

    private val diffUtilCall = object : DiffUtil.ItemCallback<Attendee>() {
        override fun areItemsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Attendee, newItem: Attendee): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtilCall)

}