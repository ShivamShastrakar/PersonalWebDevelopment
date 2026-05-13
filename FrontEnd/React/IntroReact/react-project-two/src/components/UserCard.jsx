import React from 'react'
import "./UserCard.css"

const UserCard = (props) => {
  return (
    <div className='user-container'>
        <p id='user-name'>{props.name}</p>
        <img id='user-profile' src='https://avatars.githubusercontent.com/u/105380863?v=4' alt='user profile' />
        <p id='user-title'>{props.desc}</p>
    </div>
  )
}

export default UserCard
